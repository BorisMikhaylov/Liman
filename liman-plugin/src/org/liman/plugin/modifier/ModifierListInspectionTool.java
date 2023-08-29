package org.liman.plugin.modifier;

import com.intellij.codeInspection.AbstractBaseJavaLocalInspectionTool;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.JavaElementVisitor;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiModifierList;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.util.Optional;

public abstract class ModifierListInspectionTool<A extends Annotation> extends AbstractBaseJavaLocalInspectionTool {
    private final Class<A> clazz;

    public ModifierListInspectionTool(Class<A> clazz) {
        this.clazz = clazz;
    }

    @Override
    public @NotNull PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
        return new JavaElementVisitor() {
            @Override
            public void visitAnnotation(@NotNull PsiAnnotation annotation) {
                super.visitAnnotation(annotation);
                boolean value = Optional.of(annotation)
                        .map(PsiAnnotation::resolveAnnotationType)
                        .map(a -> a.getAnnotation(clazz.getCanonicalName()))
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
                if (checkModifier(psiModifierList)) {
                    return;
                }
                registerProblem(holder, annotation, psiModifierList);
            }
        };
    }

    public abstract boolean checkModifier(PsiModifierList psiModifierList);

    public abstract void registerProblem(@NotNull ProblemsHolder holder, @NotNull PsiAnnotation annotation, PsiModifierList psiModifierList);
}
