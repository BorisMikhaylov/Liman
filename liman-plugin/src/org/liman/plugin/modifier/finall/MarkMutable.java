package org.liman.plugin.modifier.finall;

import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiModifier;
import com.intellij.psi.PsiModifierList;
import org.jetbrains.annotations.NotNull;
import org.liman.annotation.ForceMutable;
import org.liman.plugin.modifier.Marker;

public class MarkMutable extends Marker<ForceMutable> {

    public MarkMutable() {
        super(ForceMutable.class);
    }

    @Override
    public boolean checkModifier(PsiModifierList psiModifierList) {
        return !psiModifierList.hasModifierProperty(PsiModifier.FINAL);
    }

    @Override
    public void registerProblem(@NotNull ProblemsHolder holder, @NotNull PsiAnnotation annotation, PsiModifierList psiModifierList) {
        holder.registerProblem(
                annotation,
                "Annotation target should not be final",
                new MarkMutableQuickFix(psiModifierList));
    }
}

