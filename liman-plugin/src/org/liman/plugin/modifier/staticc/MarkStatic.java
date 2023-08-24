package org.liman.plugin.modifier.staticc;

import com.intellij.codeInspection.AbstractBaseJavaLocalInspectionTool;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;
import org.liman.annotation.ForceStatic;

import java.util.Optional;

public class MarkStatic extends AbstractBaseJavaLocalInspectionTool {
    @NotNull
    @Override
    public PsiElementVisitor buildVisitor(@NotNull final ProblemsHolder holder, boolean isOnTheFly) {
        return new JavaElementVisitor() {
            @Override
            public void visitAnnotation(@NotNull PsiAnnotation annotation) {
                super.visitAnnotation(annotation);
                boolean value = Optional.of(annotation)
                        .map(PsiAnnotation::resolveAnnotationType)
                        .map(a -> a.getAnnotation(ForceStatic.class.getName()))
                        .isPresent();
                if (!value) {
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
                if (psiModifierList.hasModifierProperty(PsiModifier.STATIC)) {
                    return;
                }
                holder.registerProblem(
                        annotation,
                        "Annotation target should be static",
                        new MarkStaticQuickFix(psiModifierList));
            }
        };
    }
}


