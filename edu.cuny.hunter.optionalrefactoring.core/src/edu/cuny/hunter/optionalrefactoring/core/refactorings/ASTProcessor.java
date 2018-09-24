package edu.cuny.hunter.optionalrefactoring.core.refactorings;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.dom.*;

import edu.cuny.hunter.optionalrefactoring.core.analysis.PreconditionFailure;
import edu.cuny.hunter.optionalrefactoring.core.analysis.RefactoringSettings;
import edu.cuny.hunter.optionalrefactoring.core.exceptions.HarvesterASTException;
import edu.cuny.hunter.optionalrefactoring.core.messages.Messages;

public abstract class ASTProcessor {

	private final ASTNode rootNode;
	private final RefactoringSettings settings;
	private final Set<IJavaElement> candidates = new LinkedHashSet<>();
	
	public ASTProcessor(ASTNode node, RefactoringSettings settings) {
		this.rootNode = node;
		this.settings = settings;
	}
	
	public void process() {
		if (!this.rootNode.getAST().hasResolvedBindings())
			throw new HarvesterASTException(Messages.Harvester_MissingBinding, PreconditionFailure.MISSING_BINDING, rootNode);
		this.process(this.rootNode);
	}
	
	private void process(AnonymousClassDeclaration node) { }
	private void process(ArrayAccess node) { }
	private void process(ArrayCreation node) { }
	private void process(ArrayInitializer node) { }
	private void process(ArrayType node) { }
	private void process(AssertStatement node) { }
	private void process(Assignment node) { }
	private void process(Block node) { }
	private void process(BooleanLiteral node) { }
	private void process(BreakStatement node) { }
	private void process(CastExpression node) { }
	private void process(CatchClause node) { }
	private void process(CharacterLiteral node) { }
	private void process(ClassInstanceCreation node) { }
	private void process(CompilationUnit node) { }
	private void process(ConditionalExpression node) { }
	private void process(ConstructorInvocation node) { }
	private void process(ContinueStatement node) { }
	private void process(DoStatement node) { }
	private void process(EmptyStatement node) { }
	private void process(ExpressionStatement node) {
		switch (node.getNodeType()) {
		Annotation:
	    ArrayAccess,
	    ArrayCreation,
	    ArrayInitializer,
	    Assignment,
	    BooleanLiteral,
	    CastExpression,
	    CharacterLiteral,
	    ClassInstanceCreation,
	    ConditionalExpression,
	    CreationReference,
	    ExpressionMethodReference,
	    FieldAccess,
	    InfixExpression,
	    InstanceofExpression,
	    LambdaExpression,
	    MethodInvocation,
	    MethodReference,
	    Name,
	    NullLiteral,
	    NumberLiteral,
	    ParenthesizedExpression,
	    PostfixExpression,
	    PrefixExpression,
	    StringLiteral,
	    SuperFieldAccess,
	    SuperMethodInvocation,
	    SuperMethodReference,
	    ThisExpression,
	    TypeLiteral,
	    TypeMethodReference,
	    VariableDeclarationExpression
		}
	}
	private void process(FieldAccess node) { }
	private void process(FieldDeclaration node) { }
	private void process(ForStatement node) { }
	private void process(IfStatement node) { }
	private void process(ImportDeclaration node) { }
	private void process(InfixExpression node) { }
	private void process(Initializer node) { }
	private void process(Javadoc node) { }
	private void process(LabeledStatement node) { }
	private void process(MethodDeclaration node) { }
	private void process(MethodInvocation node) { }
	private void process(NullLiteral node) { }
	private void process(NumberLiteral node) { }
	private void process(PackageDeclaration node) { }
	private void process(ParenthesizedExpression node) { }
	private void process(PostfixExpression node) { }
	private void process(PrefixExpression node) { }
	private void process(PrimitiveType node) { }
	private void process(QualifiedName node) { }
	private void process(ReturnStatement node) { }
	private void process(SimpleName node) { }
	private void process(SimpleType node) { }
	private void process(SingleVariableDeclaration node) { }
	private void process(StringLiteral node) { }
	private void process(SuperConstructorInvocation node) { }
	private void process(SuperFieldAccess node) { }
	private void process(SuperMethodInvocation node) { }
	private void process(SwitchCase node) { }
	private void process(SwitchStatement node) { }
	private void process(SynchronizedStatement node) { }
	private void process(ThisExpression node) { }
	private void process(ThrowStatement node) { }
	private void process(TryStatement node) { }
	private void process(TypeDeclaration node) { }
	private void process(TypeDeclarationStatement node) { }
	private void process(TypeLiteral node) { }
	private void process(VariableDeclarationExpression node) { }
	private void process(VariableDeclarationFragment node) { }
	private void process(VariableDeclarationStatement node) { }
	private void process(WhileStatement node) { }
	private void process(InstanceofExpression node) { }
	private void process(LineComment node) { }
	private void process(BlockComment node) { }
	private void process(TagElement node) { }
	private void process(TextElement node) { }
	private void process(MemberRef node) { }
	private void process(MethodRef node) { }
	private void process(MethodRefParameter node) { }
	private void process(EnhancedForStatement node) { }
	private void process(EnumDeclaration node) { }
	private void process(EnumConstantDeclaration node) { }
	private void process(TypeParameter node) { }
	private void process(ParameterizedType node) { }
	private void process(QualifiedType node) { }
	private void process(WildcardType node) { }
	private void process(NormalAnnotation node) { }
	private void process(MarkerAnnotation node) { }
	private void process(SingleMemberAnnotation node) { }
	private void process(MemberValuePair node) { }
	private void process(AnnotationTypeDeclaration node) { }
	private void process(Modifier node) { }
	private void process(UnionType node) { }
	private void process(Dimension node) { }
	private void process(LambdaExpression node) { }
	private void process(IntersectionType node) { }
	private void process(NameQualifiedType node) { }
	private void process(CreationReference node) { }
	private void process(ExpressionMethodReference node) { }
	private void process(SuperMethodReference node) { }
	private void process(TypeMethodReference node) { }
	private void process(ModuleDeclaration node) { }
	private void process(RequiresDirective node) { }
	private void process(ExportsDirective node) { }
	private void process(OpensDirective node) { }
	private void process(UsesDirective node) { }
	private void process(ProvidesDirective node) { }
	private void process(ModuleModifier node) { }

	private void process(ASTNode node) {
		switch(node.getNodeType()) {
		case ASTNode.ANONYMOUS_CLASS_DECLARATION:
			this.process((AnonymousClassDeclaration) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>ArrayAccess</code>.
		 * @see ArrayAccess
		 */
		case ASTNode.ARRAY_ACCESS:
			this.process((ArrayAccess) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>ArrayCreation</code>.
		 * @see ArrayCreation
		 */
		case ASTNode.ARRAY_CREATION:
			this.process((ArrayCreation) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>ArrayInitializer</code>.
		 * @see ArrayInitializer
		 */
		case ASTNode.ARRAY_INITIALIZER:
			this.process((ArrayInitializer) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>ArrayType</code>.
		 * @see ArrayType
		 */
		case ASTNode.ARRAY_TYPE:
			this.process((ArrayType) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>AssertStatement</code>.
		 * @see AssertStatement
		 */
		case ASTNode.ASSERT_STATEMENT:
			this.process((AssertStatement) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>Assignment</code>.
		 * @see Assignment
		 */
		case ASTNode.ASSIGNMENT:
			this.process((Assignment) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>Block</code>.
		 * @see Block
		 */
		case ASTNode.BLOCK:
			this.process((Block) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>BooleanLiteral</code>.
		 * @see BooleanLiteral
		 */
		case ASTNode.BOOLEAN_LITERAL:
			this.process((BooleanLiteral) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>BreakStatement</code>.
		 * @see BreakStatement
		 */
		case ASTNode.BREAK_STATEMENT:
			this.process((BreakStatement) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>CastExpression</code>.
		 * @see CastExpression
		 */
		case ASTNode.CAST_EXPRESSION:
			this.process((CastExpression) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>CatchClause</code>.
		 * @see CatchClause
		 */
		case ASTNode.CATCH_CLAUSE:
			this.process((CatchClause) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>CharacterLiteral</code>.
		 * @see CharacterLiteral
		 */
		case ASTNode.CHARACTER_LITERAL:
			this.process((CharacterLiteral) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>ClassInstanceCreation</code>.
		 * @see ClassInstanceCreation
		 */
		case ASTNode.CLASS_INSTANCE_CREATION:
			this.process((ClassInstanceCreation) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>CompilationUnit</code>.
		 * @see CompilationUnit
		 */
		case ASTNode.COMPILATION_UNIT:
			this.process((CompilationUnit) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>ConditionalExpression</code>.
		 * @see ConditionalExpression
		 */
		case ASTNode.CONDITIONAL_EXPRESSION:
			this.process((ConditionalExpression) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>ConstructorInvocation</code>.
		 * @see ConstructorInvocation
		 */
		case ASTNode.CONSTRUCTOR_INVOCATION:
			this.process((ConstructorInvocation) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>ContinueStatement</code>.
		 * @see ContinueStatement
		 */
		case ASTNode.CONTINUE_STATEMENT:
			this.process((ContinueStatement) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>DoStatement</code>.
		 * @see DoStatement
		 */
		case ASTNode.DO_STATEMENT:
			this.process((DoStatement) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>EmptyStatement</code>.
		 * @see EmptyStatement
		 */
		case ASTNode.EMPTY_STATEMENT:
			this.process((EmptyStatement) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>ExpressionStatement</code>.
		 * @see ExpressionStatement
		 */
		case ASTNode.EXPRESSION_STATEMENT:
			this.process((ExpressionStatement) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>FieldAccess</code>.
		 * @see FieldAccess
		 */
		case ASTNode.FIELD_ACCESS:
			this.process((FieldAccess) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>FieldDeclaration</code>.
		 * @see FieldDeclaration
		 */
		case ASTNode.FIELD_DECLARATION:
			this.process((FieldDeclaration) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>ForStatement</code>.
		 * @see ForStatement
		 */
		case ASTNode.FOR_STATEMENT:
			this.process((ForStatement) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>IfStatement</code>.
		 * @see IfStatement
		 */
		case ASTNode.IF_STATEMENT:
			this.process((IfStatement) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>ImportDeclaration</code>.
		 * @see ImportDeclaration
		 */
		case ASTNode.IMPORT_DECLARATION:
			this.process((ImportDeclaration) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>InfixExpression</code>.
		 * @see InfixExpression
		 */
		case ASTNode.INFIX_EXPRESSION:
			this.process((InfixExpression) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>Initializer</code>.
		 * @see Initializer
		 */
		case ASTNode.INITIALIZER:
			this.process((Initializer) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>Javadoc</code>.
		 * @see Javadoc
		 */
		case ASTNode.JAVADOC:
			this.process((Javadoc) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>LabeledStatement</code>.
		 * @see LabeledStatement
		 */
		case ASTNode.LABELED_STATEMENT:
			this.process((LabeledStatement) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>MethodDeclaration</code>.
		 * @see MethodDeclaration
		 */
		case ASTNode.METHOD_DECLARATION:
			this.process((MethodDeclaration) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>MethodInvocation</code>.
		 * @see MethodInvocation
		 */
		case ASTNode.METHOD_INVOCATION:
			this.process((MethodInvocation) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>NullLiteral</code>.
		 * @see NullLiteral
		 */
		case ASTNode.NULL_LITERAL:
			this.process((NullLiteral) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>NumberLiteral</code>.
		 * @see NumberLiteral
		 */
		case ASTNode.NUMBER_LITERAL:
			this.process((NumberLiteral) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>PackageDeclaration</code>.
		 * @see PackageDeclaration
		 */
		case ASTNode.PACKAGE_DECLARATION:
			this.process((PackageDeclaration) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>ParenthesizedExpression</code>.
		 * @see ParenthesizedExpression
		 */
		case ASTNode.PARENTHESIZED_EXPRESSION:
			this.process((ParenthesizedExpression) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>PostfixExpression</code>.
		 * @see PostfixExpression
		 */
		case ASTNode.POSTFIX_EXPRESSION:
			this.process((PostfixExpression) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>PrefixExpression</code>.
		 * @see PrefixExpression
		 */
		case ASTNode.PREFIX_EXPRESSION:
			this.process((PrefixExpression) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>PrimitiveType</code>.
		 * @see PrimitiveType
		 */
		case ASTNode.PRIMITIVE_TYPE:
			this.process((PrimitiveType) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>QualifiedName</code>.
		 * @see QualifiedName
		 */
		case ASTNode.QUALIFIED_NAME:
			this.process((QualifiedName) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>ReturnStatement</code>.
		 * @see ReturnStatement
		 */
		case ASTNode.RETURN_STATEMENT:
			this.process((ReturnStatement) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>SimpleName</code>.
		 * @see SimpleName
		 */
		case ASTNode.SIMPLE_NAME:
			this.process((SimpleName) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>SimpleType</code>.
		 * @see SimpleType
		 */
		case ASTNode.SIMPLE_TYPE:
			this.process((SimpleType) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>SingleVariableDeclaration</code>.
		 * @see SingleVariableDeclaration
		 */
		case ASTNode.SINGLE_VARIABLE_DECLARATION:
			this.process((SingleVariableDeclaration) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>StringLiteral</code>.
		 * @see StringLiteral
		 */
		case ASTNode.STRING_LITERAL:
			this.process((StringLiteral) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>SuperConstructorInvocation</code>.
		 * @see SuperConstructorInvocation
		 */
		case ASTNode.SUPER_CONSTRUCTOR_INVOCATION:
			this.process((SuperConstructorInvocation) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>SuperFieldAccess</code>.
		 * @see SuperFieldAccess
		 */
		case ASTNode.SUPER_FIELD_ACCESS:
			this.process((SuperFieldAccess) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>SuperMethodInvocation</code>.
		 * @see SuperMethodInvocation
		 */
		case ASTNode.SUPER_METHOD_INVOCATION:
			this.process((SuperMethodInvocation) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>SwitchCase</code>.
		 * @see SwitchCase
		 */
		case ASTNode.SWITCH_CASE:
			this.process((SwitchCase) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>SwitchStatement</code>.
		 * @see SwitchStatement
		 */
		case ASTNode.SWITCH_STATEMENT:
			this.process((SwitchStatement) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>SynchronizedStatement</code>.
		 * @see SynchronizedStatement
		 */
		case ASTNode.SYNCHRONIZED_STATEMENT:
			this.process((SynchronizedStatement) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>ThisExpression</code>.
		 * @see ThisExpression
		 */
		case ASTNode.THIS_EXPRESSION:
			this.process((ThisExpression) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>ThrowStatement</code>.
		 * @see ThrowStatement
		 */
		case ASTNode.THROW_STATEMENT:
			this.process((ThrowStatement) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>TryStatement</code>.
		 * @see TryStatement
		 */
		case ASTNode.TRY_STATEMENT:
			this.process((TryStatement) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>TypeDeclaration</code>.
		 * @see TypeDeclaration
		 */
		case ASTNode.TYPE_DECLARATION:
			this.process((TypeDeclaration) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>TypeDeclarationStatement</code>.
		 * @see TypeDeclarationStatement
		 */
		case ASTNode.TYPE_DECLARATION_STATEMENT:
			this.process((TypeDeclarationStatement) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>TypeLiteral</code>.
		 * @see TypeLiteral
		 */
		case ASTNode.TYPE_LITERAL:
			this.process((TypeLiteral) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>VariableDeclarationExpression</code>.
		 * @see VariableDeclarationExpression
		 */
		case ASTNode.VARIABLE_DECLARATION_EXPRESSION:
			this.process((VariableDeclarationExpression) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>VariableDeclarationFragment</code>.
		 * @see VariableDeclarationFragment
		 */
		case ASTNode.VARIABLE_DECLARATION_FRAGMENT:
			this.process((VariableDeclarationFragment) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>VariableDeclarationStatement</code>.
		 * @see VariableDeclarationStatement
		 */
		case ASTNode.VARIABLE_DECLARATION_STATEMENT:
			this.process((VariableDeclarationStatement) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>WhileStatement</code>.
		 * @see WhileStatement
		 */
		case ASTNode.WHILE_STATEMENT:
			this.process((WhileStatement) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>InstanceofExpression</code>.
		 * @see InstanceofExpression
		 */
		case ASTNode.INSTANCEOF_EXPRESSION:
			this.process((InstanceofExpression) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>LineComment</code>.
		 * @see LineComment
		 * @since 3.0
		 */
		case ASTNode.LINE_COMMENT:
			this.process((LineComment) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>BlockComment</code>.
		 * @see BlockComment
		 * @since 3.0
		 */
		case ASTNode.BLOCK_COMMENT:
			this.process((BlockComment) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>TagElement</code>.
		 * @see TagElement
		 * @since 3.0
		 */
		case ASTNode.TAG_ELEMENT:
			this.process((TagElement) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>TextElement</code>.
		 * @see TextElement
		 * @since 3.0
		 */
		case ASTNode.TEXT_ELEMENT:
			this.process((TextElement) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>MemberRef</code>.
		 * @see MemberRef
		 * @since 3.0
		 */
		case ASTNode.MEMBER_REF:
			this.process((MemberRef) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>MethodRef</code>.
		 * @see MethodRef
		 * @since 3.0
		 */
		case ASTNode.METHOD_REF:
			this.process((MethodRef) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>MethodRefParameter</code>.
		 * @see MethodRefParameter
		 * @since 3.0
		 */
		case ASTNode.METHOD_REF_PARAMETER:
			this.process((MethodRefParameter) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>EnhancedForStatement</code>.
		 * @see EnhancedForStatement
		 * @since 3.1
		 */
		case ASTNode.ENHANCED_FOR_STATEMENT:
			this.process((EnhancedForStatement) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>EnumDeclaration</code>.
		 * @see EnumDeclaration
		 * @since 3.1
		 */
		case ASTNode.ENUM_DECLARATION:
			this.process((EnumDeclaration) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>EnumConstantDeclaration</code>.
		 * @see EnumConstantDeclaration
		 * @since 3.1
		 */
		case ASTNode.ENUM_CONSTANT_DECLARATION:
			this.process((EnumConstantDeclaration) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>TypeParameter</code>.
		 * @see TypeParameter
		 * @since 3.1
		 */
		case ASTNode.TYPE_PARAMETER:
			this.process((TypeParameter) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>ParameterizedType</code>.
		 * @see ParameterizedType
		 * @since 3.1
		 */
		case ASTNode.PARAMETERIZED_TYPE:
			this.process((ParameterizedType) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>QualifiedType</code>.
		 * @see QualifiedType
		 * @since 3.1
		 */
		case ASTNode.QUALIFIED_TYPE:
			this.process((QualifiedType) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>WildcardType</code>.
		 * @see WildcardType
		 * @since 3.1
		 */
		case ASTNode.WILDCARD_TYPE:
			this.process((WildcardType) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>NormalAnnotation</code>.
		 * @see NormalAnnotation
		 * @since 3.1
		 */
		case ASTNode.NORMAL_ANNOTATION:
			this.process((NormalAnnotation) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>MarkerAnnotation</code>.
		 * @see MarkerAnnotation
		 * @since 3.1
		 */
		case ASTNode.MARKER_ANNOTATION:
			this.process((MarkerAnnotation) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>SingleMemberAnnotation</code>.
		 * @see SingleMemberAnnotation
		 * @since 3.1
		 */
		case ASTNode.SINGLE_MEMBER_ANNOTATION:
			this.process((SingleMemberAnnotation) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>MemberValuePair</code>.
		 * @see MemberValuePair
		 * @since 3.1
		 */
		case ASTNode.MEMBER_VALUE_PAIR:
			this.process((MemberValuePair) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>AnnotationTypeDeclaration</code>.
		 * @see AnnotationTypeDeclaration
		 * @since 3.1
		 */
		case ASTNode.ANNOTATION_TYPE_DECLARATION:
			this.process((AnnotationTypeDeclaration) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>AnnotationTypeMemberDeclaration</code>.
		 * @see AnnotationTypeMemberDeclaration
		 * @since 3.1
		 */
		case ASTNode.ANNOTATION_TYPE_MEMBER_DECLARATION:
			this.process((AnnotationTypeMemberDeclaration) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>Modifier</code>.
		 * @see Modifier
		 * @since 3.1
		 */
		case ASTNode.MODIFIER:
			this.process((Modifier) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>UnionType</code>.
		 * @see UnionType
		 * @since 3.7.1
		 */
		case ASTNode.UNION_TYPE:
			this.process((UnionType) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>Dimension</code>.
		 *
		 * @see Dimension
		 * @since 3.10
		 */
		case ASTNode.DIMENSION:
			this.process((Dimension) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>LambdaExpression</code>.
		 * @see LambdaExpression
		 * @since 3.10
		 */
		case ASTNode.LAMBDA_EXPRESSION:
			this.process((LambdaExpression) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>IntersectionType</code>.
		 *
		 * @see IntersectionType
		 * @since 3.10
		 */
		case ASTNode.INTERSECTION_TYPE:
			this.process((IntersectionType) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>NameQualifiedType</code>.
		 * @see NameQualifiedType
		 * @since 3.10
		 */
		case ASTNode.NAME_QUALIFIED_TYPE:
			this.process((NameQualifiedType) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>CreationReference</code>.
		 * @see CreationReference
		 * @since 3.10
		 */
		case ASTNode.CREATION_REFERENCE:
			this.process((CreationReference) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>ExpressionMethodReference</code>.
		 * @see ExpressionMethodReference
		 * @since 3.10
		 */
		case ASTNode.EXPRESSION_METHOD_REFERENCE:
			this.process((ExpressionMethodReference) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>SuperMethhodReference</code>.
		 * @see SuperMethodReference
		 * @since 3.10
		 */
		case ASTNode.SUPER_METHOD_REFERENCE:
			this.process((SuperMethodReference) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>TypeMethodReference</code>.
		 * @see TypeMethodReference
		 * @since 3.10
		 */
		case ASTNode.TYPE_METHOD_REFERENCE:
			this.process((TypeMethodReference) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>ModuleDeclaration</code>.
		 * @see ModuleDeclaration
		 * @since 3.14
		 */
		case ASTNode.MODULE_DECLARATION:
			this.process((ModuleDeclaration) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>RequiresDirective</code>.
		 * @see RequiresDirective
		 * @since 3.14
		 */
		case ASTNode.REQUIRES_DIRECTIVE:
			this.process((RequiresDirective) node);
			break;
		
		/**
		 * Node type constant indicating a node of type
		 * <code>ExportsDirective</code>.
		 * @see ExportsDirective
		 * @since 3.14
		 */
		case ASTNode.EXPORTS_DIRECTIVE:
			this.process((ExportsDirective) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>OpensDirective</code>.
		 * @see OpensDirective
		 * @since 3.14
		 */
		case ASTNode.OPENS_DIRECTIVE:
			this.process((OpensDirective) node);
			break;
		
		/**
		 * Node type constant indicating a node of type
		 * <code>UsesDirective</code>.
		 * @see UsesDirective
		 * @since 3.14
		 */
		case ASTNode.USES_DIRECTIVE:
			this.process((UsesDirective) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>ProvidesDirective</code>.
		 * @see ProvidesDirective
		 * @since 3.14
		 */
		case ASTNode.PROVIDES_DIRECTIVE:
			this.process((ProvidesDirective) node);
			break;

		/**
		 * Node type constant indicating a node of type
		 * <code>ModuleModifier</code>.
		 * @see ModuleModifier
		 * @since 3.14
		 */
		case ASTNode.MODULE_MODIFIER:
			this.process((ModuleModifier) node);
			break;
		}
	}
}
