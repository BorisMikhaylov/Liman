package org.liman.plugin.modifier.finall;

import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiModifier;
import com.intellij.psi.PsiModifierList;
import org.jetbrains.annotations.NotNull;
import org.liman.annotation.ForceFinal;
import org.liman.plugin.modifier.Marker;

public class MarkFinal extends Marker<ForceFinal> {

    public MarkFinal() {
        super(ForceFinal.class);
    }

    @Override
    public boolean checkModifier(PsiModifierList psiModifierList) {
        return psiModifierList.hasModifierProperty(PsiModifier.FINAL);
    }

    @Override
    public void registerProblem(@NotNull ProblemsHolder holder, @NotNull PsiAnnotation annotation, PsiModifierList psiModifierList) {
        holder.registerProblem(
                annotation,
                "Annotation target should be final",
                new MarkFinalQuickFix(psiModifierList));
    }
}


