package org.liman.plugin.modifier.finall;

import com.intellij.codeInspection.AbstractBaseJavaLocalInspectionTool;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;
import org.liman.annotation.ForceFinal;

import java.util.Optional;

public class MarkFinal extends AbstractBaseJavaLocalInspectionTool {
    @NotNull
    @Override
    public PsiElementVisitor buildVisitor(@NotNull final ProblemsHolder holder, boolean isOnTheFly) {
        return new JavaElementVisitor() {
            @Override
            public void visitAnnotation(@NotNull PsiAnnotation annotation) {
                super.visitAnnotation(annotation);
                Boolean value = Optional.of(annotation)
                        .map(PsiAnnotation::resolveAnnotationType)
                        .map(a -> a.getAnnotation(ForceFinal.class.getName()))
                        .map(a -> a.findAttributeValue("value"))
                        .filter(PsiLiteralExpression.class::isInstance)
                        .map(PsiLiteralExpression.class::cast)
                        .map(PsiLiteralExpression::getValue)
                        .filter(Boolean.class::isInstance)
                        .map(Boolean.class::cast)
                        .orElse(null);
                if (value == null) {
                    return;
                }
                PsiModifierList psiModifierList = Optional.of(annotation)
                        .map(PsiAnnotation::getParent)
                        .filter(PsiModifierList.class::isInstance)
                        .map(PsiModifierList.class::cast)
                        .orElse(null);
                if (psiModifierList == null) {
                    return;
                }
                if (psiModifierList.hasModifierProperty(PsiModifier.FINAL) == value) {
                    return;
                }
                holder.registerProblem(
                        annotation,
                        "Annotation target should" + (value ? "" : " not") + " be final",
                        new MarkFinalQuickFix(psiModifierList));
            }
        };
    }
}


