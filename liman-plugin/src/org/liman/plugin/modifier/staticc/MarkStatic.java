package org.liman.plugin.modifier.staticc;

import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiModifier;
import com.intellij.psi.PsiModifierList;
import org.jetbrains.annotations.NotNull;
import org.liman.annotation.ForceStatic;
import org.liman.plugin.modifier.Marker;

public class MarkStatic extends Marker<ForceStatic> {

    public MarkStatic() {
        super(ForceStatic.class);
    }

    @Override
    public boolean checkModifier(PsiModifierList psiModifierList) {
        return psiModifierList.hasModifierProperty(PsiModifier.STATIC);
    }

    @Override
    public void registerProblem(@NotNull ProblemsHolder holder, @NotNull PsiAnnotation annotation, PsiModifierList psiModifierList) {
        holder.registerProblem(
                annotation,
                "Annotation target should be static",
                new MarkStaticQuickFix(psiModifierList));
    }
}


