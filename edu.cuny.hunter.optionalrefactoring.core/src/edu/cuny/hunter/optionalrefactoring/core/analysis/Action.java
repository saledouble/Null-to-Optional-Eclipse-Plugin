package edu.cuny.hunter.optionalrefactoring.core.analysis;

import java.util.EnumSet;

import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.SuperFieldAccess;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

/**
 * @author oren The types of transformations that can be made on the AST.
 */
public enum Action {

	/**
	 * Take No Action
	 */
	NIL,
	/**
	 * Transform to a parameterized optional type and wrap value if any.
	 */
	CHANGE_N2O_VAR_DECL,
	/**
	 * Transform the right side of the declaration whose type is now Optional into
	 * it's raw type or null
	 */
	BRIDGE_N2O_VAR_DECL,
	/**
	 * Transform to a parameterized optional type
	 */
	CHANGE_N2O_PARAM,
	/**
	 * Transform to a parameterized optional return type and wrap return value;
	 */
	CHANGE_N2O_METH_DECL,
	/**
	 * Transform the value of a variable or invocation with optional type to it's
	 * raw type or null
	 */
	BRIDGE_VALUE_OUT,
	/**
	 * Transform the literal on the right hand side of an assignment into an
	 * optional.
	 */
	CHANGE_N2O_LITERAL,
	/**
	 * Wrap a value in an Optional.ofNullable
	 */
	BRIDGE_VALUE_IN;

	public static Action infer(final ArrayAccess node, final EnumSet<PreconditionFailure> pf,
			final RefactoringSettings settings) {
		return NIL;
	}

	public static Action infer(final ArrayCreation node, final EnumSet<PreconditionFailure> pf,
			final RefactoringSettings settings) {
		return NIL;
	}

	public static Action infer(final CastExpression node, final EnumSet<PreconditionFailure> pf,
			final RefactoringSettings settings) {
		return NIL;
	}

	/**
	 * Determines appropriate action for ClassInstanceCreation (we just wrap it with
	 * Optional::ofNullable)
	 *
	 * @param node
	 * @param element
	 * @param pf
	 * @param settings
	 * @return
	 */
	public static Action infer(final ClassInstanceCreation node, final IMethod element,
			final EnumSet<PreconditionFailure> pf, final RefactoringSettings settings) {
		return BRIDGE_VALUE_IN;
	}

	public static Action infer(final EnhancedForStatement node, final EnumSet<PreconditionFailure> pf,
			final RefactoringSettings settings) {
		return NIL;
	}

	/**
	 * Determines appropriate action for a method receiver
	 *
	 * @param expression
	 * @param element
	 * @param pf
	 * @param settings
	 * @return
	 */
	public static Action infer(final Expression expression, final IMethod element,
			final EnumSet<PreconditionFailure> pf, final RefactoringSettings settings) {
		return BRIDGE_VALUE_OUT;
	}

	public static Action infer(final FieldAccess node, final IField element, final EnumSet<PreconditionFailure> pf,
			final RefactoringSettings settings) {
		return NIL;
	}

	public static Action infer(final FieldDeclaration node, final IField element, final EnumSet<PreconditionFailure> pf,
			final RefactoringSettings settings) {
		return NIL;
	}

	public static Action infer(final InfixExpression node, final EnumSet<PreconditionFailure> pf,
			final RefactoringSettings settings) {
		return NIL;
	}

	public static Action infer(final MethodDeclaration node, final IMethod element,
			final EnumSet<PreconditionFailure> pf, final RefactoringSettings settings) {
		return NIL;
	}

	public static Action infer(final MethodInvocation node, final IMethod element,
			final EnumSet<PreconditionFailure> pf, final RefactoringSettings settings) {
		return NIL;
	}

	public static Action infer(final Name node, final IJavaElement element, final EnumSet<PreconditionFailure> pf,
			final RefactoringSettings settings) {
		return NIL;
	}

	public static Action infer(final SingleVariableDeclaration node, final IJavaElement element,
			final EnumSet<PreconditionFailure> pf, final RefactoringSettings settings) {
		return NIL;
	}

	public static Action infer(final SuperFieldAccess node, final IField element, final EnumSet<PreconditionFailure> pf,
			final RefactoringSettings settings) {
		return NIL;
	}

	public static Action infer(final SuperMethodInvocation node, final IMethod element,
			final EnumSet<PreconditionFailure> pf, final RefactoringSettings settings) {
		return NIL;
	}

	public static Action infer(final VariableDeclarationExpression node, final IJavaElement element,
			final EnumSet<PreconditionFailure> pf, final RefactoringSettings settings) {
		return NIL;
	}

	public static Action infer(final VariableDeclarationFragment node, final IField element,
			final EnumSet<PreconditionFailure> pf, final RefactoringSettings settings) {
		return NIL;
	}

	public static Action infer(final VariableDeclarationFragment node, final IJavaElement element,
			final EnumSet<PreconditionFailure> pf, final RefactoringSettings settings) {
		return NIL;
	}

	public static Action infer(final VariableDeclarationStatement node, final IJavaElement element,
			final EnumSet<PreconditionFailure> pf, final RefactoringSettings settings) {
		return NIL;
	}
}
