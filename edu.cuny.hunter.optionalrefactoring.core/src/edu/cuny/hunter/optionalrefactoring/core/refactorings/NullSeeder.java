package edu.cuny.hunter.optionalrefactoring.core.refactorings;

import java.util.AbstractMap.SimpleEntry;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.ILocalVariable;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.ConstructorInvocation;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.NullLiteral;
import org.eclipse.jdt.core.dom.ParenthesizedExpression;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.SuperConstructorInvocation;
import org.eclipse.jdt.core.dom.SuperFieldAccess;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

import com.google.common.collect.Sets;

import edu.cuny.hunter.optionalrefactoring.core.analysis.PreconditionFailure;
import edu.cuny.hunter.optionalrefactoring.core.exceptions.HarvesterASTException;
import edu.cuny.hunter.optionalrefactoring.core.exceptions.HarvesterJavaModelException;
import edu.cuny.hunter.optionalrefactoring.core.messages.Messages;
import edu.cuny.hunter.optionalrefactoring.core.utils.Util;

/**
 * @author <a href="mailto:ofriedman@acm.org">Oren Friedman</a>
 *
 */
/**
 * @author oren
 * This class processes source files for instances of NullLiteral expressions
 *  and extracts the locally type dependent entity, if any can be extracted,
 *  in the form of a singleton TypeDependentElementSet with a RefactoringStatus
 *  indicating whether or not the entity can be refactored.
 *
 */
class NullSeeder {

	private final ASTNode refactoringRootNode;
	private final Set<TypeDependentElementSet> candidates = new LinkedHashSet<>();

	private ASTNode currentNull;

	public NullSeeder(ASTNode node) {
		this.refactoringRootNode = node;
	}
	
	public Set<TypeDependentElementSet> getPassing() {
		if (this.candidates.isEmpty()) 
			return this.candidates.stream().filter(set -> set.getStatus().isOK())
				.collect(Collectors.toSet());
		else return Sets.newHashSet();
	}
	
	public Set<TypeDependentElementSet> getFailing() {
		if (this.candidates.isEmpty()) 
			return this.candidates.stream().filter(set -> !set.getStatus().isOK())
				.collect(Collectors.toSet());
		else return Sets.newHashSet();
	}
	/**
	 * @return Map of IJavaElement to whether it is implicitly null field
	 */
	boolean seedNulls() {
		ASTVisitor visitor = new ASTVisitor() {
			@Override
			public boolean visit(NullLiteral nl) {
				// set the currently-being-traversed node for this object
				NullSeeder.this.currentNull = nl;
				try {	// try to process the node
					NullSeeder.this.process(nl.getParent());
				} catch (HarvesterASTException hae) {	// catch any exceptions originating in AST traversal
					SimpleEntry<IJavaElement,RefactoringStatus> ret = PreconditionFailure.handleFailure(hae);
					if (ret.getKey() != null) 
						NullSeeder.this.candidates.add(TypeDependentElementSet.createBadSeed(
								ret.getKey(), Boolean.FALSE, ret.getValue()));
					else Logger.getAnonymousLogger().warning(
							Messages.NullLiteralFailed+hae.getNode().toString());
				} catch (HarvesterJavaModelException hje) {	
					// catch any exceptions originating in Java Model traversal
					SimpleEntry<IJavaElement,RefactoringStatus> ret = PreconditionFailure.handleFailure(hje);
					if (ret.getKey() != null) 
						NullSeeder.this.candidates.add(
								TypeDependentElementSet.createBadSeed(
										ret.getKey(), Boolean.FALSE, ret.getValue()));
					else Logger.getAnonymousLogger().warning(
							Messages.NullLiteralFailed+hje.getElement().toString());
				}
				return super.visit(nl);
			}
			/* (non-Javadoc)
			 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.VariableDeclarationFragment)
			 * here we are just processing to find un-initialized (implicitly null) Field declarations.
			 * All processing is done inside the visitor.
			 */
			@Override
			public boolean visit(VariableDeclarationFragment node) {
				NullSeeder.this.currentNull = node;
				try {
					IVariableBinding binding = Util.resolveBinding(node);
					IJavaElement element = Util.resolveElement(node);
					if (element instanceof IField) {
						if (node.getInitializer() == null)
							/*this element gets added to the Map candidates with 
							 * boolean true indicating an implicit null
							 * also, if the type of the declaration is primitive,
							 * we ignore it
							 * */
							if (!binding.getVariableDeclaration().getType().isPrimitive()) {
								if (element.isReadOnly()) throw new HarvesterJavaModelException(
										Messages.Harvester_SourceNotPresent,
										PreconditionFailure.READ_ONLY_ELEMENT,
										element);
								if (Util.isBinaryCode(element)) throw new HarvesterJavaModelException(
										Messages.Harvester_SourceNotPresent,
										PreconditionFailure.BINARY_ELEMENT,
										element);
								if (Util.isGeneratedCode(element)) throw new HarvesterJavaModelException(
										Messages.Harvester_SourceNotPresent,
										PreconditionFailure.GENERATED_ELEMENT,
										element);
								NullSeeder.this.candidates.add(
										TypeDependentElementSet.createSeed(element,Boolean.TRUE));
							}
					}
				} catch (HarvesterASTException hae) {
					SimpleEntry<IJavaElement,RefactoringStatus> ret = PreconditionFailure.handleFailure(hae);
					if (ret.getKey() != null) NullSeeder.this.candidates.add(
							TypeDependentElementSet.createBadSeed(
									ret.getKey(), Boolean.FALSE, ret.getValue()));
					else Logger.getAnonymousLogger().warning(
							Messages.NullLiteralFailed+hae.getNode().toString());
				} catch (HarvesterJavaModelException hje) {
					SimpleEntry<IJavaElement,RefactoringStatus> ret = PreconditionFailure.handleFailure(hje);
					if (ret.getKey() != null) NullSeeder.this.candidates.add(
							TypeDependentElementSet.createBadSeed(
									ret.getKey(), Boolean.FALSE, ret.getValue()));
					else Logger.getAnonymousLogger().warning(
							Messages.NullLiteralFailed+hje.getElement().toString());
				}
				return super.visit(node);
			}
		};
		this.refactoringRootNode.accept(visitor);
		return this.candidates.stream().anyMatch(set -> set.getStatus().isOK());
	}

	private <T extends ASTNode> ASTNode getContaining(Class<T> type, ASTNode node) {
		ASTNode curr = node;
		while (curr != null && (curr.getClass() != type)) {
			curr = curr.getParent();
		}
		if (curr != null) return curr;
		throw new HarvesterASTException(
				Messages.Harvester_ASTNodeError+node.getClass().getSimpleName(),
				PreconditionFailure.AST_ERROR,
				node);
	}

	/**
	 * @param node Any of the possible AST nodes where a null literal could appear as an immediate child
	 * @throws JavaModelException 
	 */
	private void process(ASTNode node) {
		switch (node.getNodeType()) {
		case ASTNode.ASSIGNMENT : this.process(((Assignment)node).getLeftHandSide());
		break;
		case ASTNode.RETURN_STATEMENT : this.process((ReturnStatement)node);
		break;
		case ASTNode.METHOD_INVOCATION : this.process((MethodInvocation)node);
		break;
		case ASTNode.SUPER_METHOD_INVOCATION : this.process((SuperMethodInvocation)node);
		break;
		case ASTNode.CONSTRUCTOR_INVOCATION : this.process((ConstructorInvocation)node);
		break;
		case ASTNode.SUPER_CONSTRUCTOR_INVOCATION : this.process((SuperConstructorInvocation)node);
		break;
		case ASTNode.CLASS_INSTANCE_CREATION : this.process((ClassInstanceCreation)node);
		break;
		case ASTNode.VARIABLE_DECLARATION_FRAGMENT : this.process((VariableDeclarationFragment)node);
		break;
		case ASTNode.ARRAY_INITIALIZER : this.process((ArrayInitializer)node);
		break;
		case ASTNode.PARENTHESIZED_EXPRESSION : this.process((ParenthesizedExpression)node);
		break;
		case ASTNode.CONDITIONAL_EXPRESSION : this.process((ConditionalExpression)node);
		break;
		case ASTNode.SINGLE_VARIABLE_DECLARATION : this.process((SingleVariableDeclaration)node);
		break;
		case ASTNode.CAST_EXPRESSION : this.process((CastExpression)node);
		break;
		case ASTNode.INFIX_EXPRESSION : /*This may appear in some edge cases, we do nothing*/
			break;
		default : throw new HarvesterASTException(
				Messages.Harvester_ASTNodeError+node.getClass().getSimpleName(),
				PreconditionFailure.AST_ERROR,
				node);
		}
	}

	private void process(Expression node) throws HarvesterASTException {
		switch (node.getNodeType()) {
		case ASTNode.QUALIFIED_NAME : process((Name)node);
		break;
		case ASTNode.SIMPLE_NAME : process((Name)node);
		break;
		case ASTNode.ARRAY_ACCESS : process((ArrayAccess)node);
		break;
		case ASTNode.FIELD_ACCESS : process((FieldAccess)node);
		break;
		case ASTNode.SUPER_FIELD_ACCESS : process((SuperFieldAccess)node);
		break;
		default : throw new HarvesterASTException(
				Messages.Harvester_ASTNodeError+node.getClass().getSimpleName(), 
				PreconditionFailure.AST_ERROR,
				node);
		}
	}

	private void process(CastExpression node) {
		// Cast expressions cannot be refactored as Optional
		throw new HarvesterASTException(Messages.Harvester_CastExpression, 
				PreconditionFailure.CAST_EXPRESSION,
				node);
	}

	private void process(ConditionalExpression node) {
		ASTNode parent = node.getParent();
		if (parent != null) process(parent);
		else throw new HarvesterASTException(Messages.Harvester_ASTNodeError+node.getClass().getSimpleName(), 
				PreconditionFailure.AST_ERROR,
				node);
	}

	private void process(ParenthesizedExpression node) {
		ASTNode parent = node.getParent();
		if (parent != null) process(parent);
		else throw new HarvesterASTException(Messages.Harvester_ASTNodeError+node.getClass().getSimpleName(), 
				PreconditionFailure.AST_ERROR,
				node);
	}

	private void process(ReturnStatement node) throws HarvesterASTException {
		ASTNode methodDecl = getContaining(MethodDeclaration.class, node); 
		if (methodDecl instanceof MethodDeclaration){
			IJavaElement im = Util.resolveElement((MethodDeclaration)methodDecl);
			this.candidates.add(TypeDependentElementSet.createSeed(im,Boolean.FALSE));
		} else throw new HarvesterASTException(Messages.Harvester_ASTNodeError+node.getClass().getSimpleName(), 
				PreconditionFailure.AST_ERROR,
				node);
	}

	private void process(Name node) throws HarvesterASTException {
		IJavaElement element = Util.resolveElement(node);
		this.candidates.add(TypeDependentElementSet.createSeed(element,Boolean.FALSE));
	}

	private void process(SuperFieldAccess node) throws HarvesterASTException {
		switch (node.getNodeType()) {
		case ASTNode.SUPER_FIELD_ACCESS : {
			IJavaElement element = Util.resolveElement(node);
			if (element.isReadOnly()) throw new HarvesterJavaModelException(
					Messages.Harvester_SourceNotPresent,
					PreconditionFailure.READ_ONLY_ELEMENT,
					element);
			if (Util.isBinaryCode(element)) throw new HarvesterJavaModelException(
					Messages.Harvester_SourceNotPresent,
					PreconditionFailure.BINARY_ELEMENT,
					element);
			if (Util.isGeneratedCode(element)) throw new HarvesterJavaModelException(
					Messages.Harvester_SourceNotPresent,
					PreconditionFailure.GENERATED_ELEMENT,
					element);
			NullSeeder.this.candidates.add(
					TypeDependentElementSet.createSeed(element,Boolean.TRUE));
			this.candidates.add(TypeDependentElementSet.createSeed(element,Boolean.FALSE));
		}
		default : throw new HarvesterASTException(Messages.Harvester_ASTNodeError+node.getClass().getSimpleName(),
				PreconditionFailure.AST_ERROR,
				node);
		}
	}

	private void process(FieldAccess node) throws HarvesterASTException {
		switch (node.getNodeType()) {
		case ASTNode.FIELD_ACCESS : {
			IJavaElement element = Util.resolveElement(node);
			if (element.isReadOnly()) throw new HarvesterJavaModelException(
					Messages.Harvester_SourceNotPresent,
					PreconditionFailure.READ_ONLY_ELEMENT,
					element);
			if (Util.isBinaryCode(element)) throw new HarvesterJavaModelException(
					Messages.Harvester_SourceNotPresent,
					PreconditionFailure.BINARY_ELEMENT,
					element);
			if (Util.isGeneratedCode(element)) throw new HarvesterJavaModelException(
					Messages.Harvester_SourceNotPresent,
					PreconditionFailure.GENERATED_ELEMENT,
					element);
			this.candidates.add(TypeDependentElementSet.createSeed(element,Boolean.FALSE));
		}
		default : throw new HarvesterASTException(Messages.Harvester_ASTNodeError+node.getClass().getSimpleName(), 
				PreconditionFailure.AST_ERROR,
				node);
		}
	}

	private void process(ArrayInitializer node) {
		ASTNode arrayCreationOrVariableDeclarationFragment = node.getParent();
		switch (arrayCreationOrVariableDeclarationFragment.getNodeType()) {
		case ASTNode.ARRAY_CREATION : {
			ASTNode target = arrayCreationOrVariableDeclarationFragment.getParent();
			if (target != null) {
				process(target);
				break;
			}
		}
		case ASTNode.VARIABLE_DECLARATION_FRAGMENT : process(arrayCreationOrVariableDeclarationFragment);
		break;
		default : throw new HarvesterASTException(Messages.Harvester_ASTNodeError+node.getClass().getSimpleName(), 
				PreconditionFailure.AST_ERROR,
				node);
		}
	}

	private void process(ArrayAccess node) throws HarvesterASTException {
		switch (node.getNodeType()) {
		case ASTNode.ARRAY_ACCESS : {
			Expression e = ((ArrayAccess)node).getArray();
			process(e);
			break;
		}
		default : throw new HarvesterASTException(Messages.Harvester_ASTNodeError+node.getClass().getSimpleName(), 
				PreconditionFailure.AST_ERROR,
				node);
		}
	}

	@SuppressWarnings("unchecked")
	private void process(ClassInstanceCreation node) throws HarvesterASTException {
		int argPos = Util.getParamNumber(node.arguments(), (Expression)this.currentNull);
		IMethod method = (IMethod) Util.resolveElement(node);
		try {
			ILocalVariable[] params = method.getParameters();
			ILocalVariable targetParam = params[argPos];
			if (targetParam.isReadOnly()) throw new HarvesterJavaModelException(
					Messages.Harvester_SourceNotPresent,
					PreconditionFailure.READ_ONLY_ELEMENT,
					targetParam);
			if (Util.isBinaryCode(method)) throw new HarvesterJavaModelException(
					Messages.Harvester_SourceNotPresent,
					PreconditionFailure.BINARY_ELEMENT,
					targetParam);
			if (Util.isGeneratedCode(targetParam)) throw new HarvesterJavaModelException(
					Messages.Harvester_SourceNotPresent,
					PreconditionFailure.GENERATED_ELEMENT,
					targetParam);
			this.candidates.add(TypeDependentElementSet.createSeed(targetParam, Boolean.FALSE));
		} catch (JavaModelException e) {
			throw new HarvesterJavaModelException(Messages.Harvester_MissingJavaElement+method.getClass().getSimpleName(),
					PreconditionFailure.MISSING_JAVA_ELEMENT,
					method);
		}
	}

	@SuppressWarnings("unchecked")
	private void process(MethodInvocation node) throws HarvesterASTException {
		int argPos = Util.getParamNumber(node.arguments(), (Expression)this.currentNull);
		IMethod method = (IMethod) Util.resolveElement(node);			
		try {
			ILocalVariable[] params = method.getParameters();
			ILocalVariable targetParam = params[argPos];
			if (targetParam.isReadOnly()) throw new HarvesterJavaModelException(
					Messages.Harvester_SourceNotPresent,
					PreconditionFailure.READ_ONLY_ELEMENT,
					targetParam);
			if (Util.isBinaryCode(method)) throw new HarvesterJavaModelException(
					Messages.Harvester_SourceNotPresent,
					PreconditionFailure.BINARY_ELEMENT,
					targetParam);
			if (Util.isGeneratedCode(targetParam)) throw new HarvesterJavaModelException(
					Messages.Harvester_SourceNotPresent,
					PreconditionFailure.GENERATED_ELEMENT,
					targetParam);
			this.candidates.add(TypeDependentElementSet.createSeed(targetParam, Boolean.FALSE));
		} catch (JavaModelException e) {
			throw new HarvesterJavaModelException(Messages.Harvester_MissingJavaElement+method.getClass().getSimpleName(),
					PreconditionFailure.MISSING_JAVA_ELEMENT,
					method);
		}
	}

	@SuppressWarnings("unchecked")
	private void process(SuperMethodInvocation node) throws HarvesterASTException {
		int argPos = Util.getParamNumber(node.arguments(), (Expression)this.currentNull);
		IMethod method = (IMethod)Util.resolveElement(node);				
		try {
			ILocalVariable[] params = method.getParameters();
			ILocalVariable targetParam = params[argPos];
			if (targetParam.isReadOnly()) throw new HarvesterJavaModelException(
					Messages.Harvester_SourceNotPresent,
					PreconditionFailure.READ_ONLY_ELEMENT,
					targetParam);
			if (Util.isBinaryCode(method)) throw new HarvesterJavaModelException(
					Messages.Harvester_SourceNotPresent,
					PreconditionFailure.BINARY_ELEMENT,
					targetParam);
			if (Util.isGeneratedCode(targetParam)) throw new HarvesterJavaModelException(
					Messages.Harvester_SourceNotPresent,
					PreconditionFailure.GENERATED_ELEMENT,
					targetParam);
			this.candidates.add(TypeDependentElementSet.createSeed(targetParam, Boolean.FALSE));
		} catch (JavaModelException e) {
			throw new HarvesterJavaModelException(Messages.Harvester_MissingJavaElement+method.getClass().getSimpleName(),
					PreconditionFailure.MISSING_JAVA_ELEMENT,
					method);
		}
	}

	@SuppressWarnings("unchecked")
	private void process(ConstructorInvocation node) throws HarvesterASTException {
		int argPos = Util.getParamNumber(node.arguments(), (Expression)this.currentNull);
		IMethod method = (IMethod)Util.resolveElement(node);				
		try {
			ILocalVariable[] params = method.getParameters();
			ILocalVariable targetParam = params[argPos];
			if (targetParam.isReadOnly()) throw new HarvesterJavaModelException(
					Messages.Harvester_SourceNotPresent,
					PreconditionFailure.READ_ONLY_ELEMENT,
					targetParam);
			if (Util.isBinaryCode(method)) throw new HarvesterJavaModelException(
					Messages.Harvester_SourceNotPresent,
					PreconditionFailure.BINARY_ELEMENT,
					targetParam);
			if (Util.isGeneratedCode(targetParam)) throw new HarvesterJavaModelException(
					Messages.Harvester_SourceNotPresent,
					PreconditionFailure.GENERATED_ELEMENT,
					targetParam);
			this.candidates.add(TypeDependentElementSet.createSeed(targetParam, Boolean.FALSE));
		} catch (JavaModelException e) {
			throw new HarvesterJavaModelException(Messages.Harvester_MissingJavaElement+method.getClass().getSimpleName(),
					PreconditionFailure.MISSING_JAVA_ELEMENT,
					method);
		}
	}

	@SuppressWarnings("unchecked")
	private void process(SuperConstructorInvocation node) throws HarvesterASTException {
		int argPos = Util.getParamNumber(node.arguments(), (Expression)this.currentNull);
		IMethod method = (IMethod)Util.resolveElement(node);				
		try {
			ILocalVariable[] params = method.getParameters();
			ILocalVariable targetParam = params[argPos];
			if (targetParam.isReadOnly()) throw new HarvesterJavaModelException(
					Messages.Harvester_SourceNotPresent,
					PreconditionFailure.READ_ONLY_ELEMENT,
					targetParam);
			if (Util.isBinaryCode(method)) throw new HarvesterJavaModelException(
					Messages.Harvester_SourceNotPresent,
					PreconditionFailure.BINARY_ELEMENT,
					targetParam);
			if (Util.isGeneratedCode(targetParam)) throw new HarvesterJavaModelException(
					Messages.Harvester_SourceNotPresent,
					PreconditionFailure.GENERATED_ELEMENT,
					targetParam);
			this.candidates.add(TypeDependentElementSet.createSeed(targetParam, Boolean.FALSE));
		} catch (JavaModelException e) {
			throw new HarvesterJavaModelException(Messages.Harvester_MissingJavaElement+method.getClass().getSimpleName(),
					PreconditionFailure.MISSING_JAVA_ELEMENT,
					method);
		}
	}

	@SuppressWarnings("unchecked")
	private void process(VariableDeclarationFragment node) throws HarvesterASTException {
		ASTNode parent = node.getParent();
		List<VariableDeclarationFragment> fragments;
		switch (parent.getNodeType()) {
		case ASTNode.FIELD_DECLARATION : fragments = ((FieldDeclaration)parent).fragments();
		break;
		case ASTNode.VARIABLE_DECLARATION_EXPRESSION :	fragments = ((VariableDeclarationExpression)parent).fragments();
		break;
		case ASTNode.VARIABLE_DECLARATION_STATEMENT : fragments = ((VariableDeclarationStatement)parent).fragments();
		break;
		default : throw new HarvesterASTException(Messages.Harvester_ASTNodeError+node.getClass().getSimpleName(), 
				PreconditionFailure.AST_ERROR,
				parent);
		}
		Set<TypeDependentElementSet> elements = new LinkedHashSet<>();
		for (Object o : fragments) {
			VariableDeclarationFragment vdf = (VariableDeclarationFragment)o;
			IJavaElement element = Util.resolveElement(vdf);
			elements.add(TypeDependentElementSet.createSeed(element,Boolean.FALSE));
		}
		this.candidates.addAll(elements);
	}

	private void process(SingleVariableDeclaration node) throws HarvesterASTException {
		// Single variable declaration nodes are used in a limited number of places, including formal parameter lists and catch clauses. They are not used for field declarations and regular variable declaration statements. 
		IJavaElement element = Util.resolveElement(node);
		this.candidates.add(TypeDependentElementSet.createSeed(element,Boolean.FALSE));
	}
}
