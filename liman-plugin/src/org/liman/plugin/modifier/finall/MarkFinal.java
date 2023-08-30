package org.liman.plugin.modifier.finall;

import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiModifier;
import com.intellij.psi.PsiModifierList;
import org.jetbrains.annotations.NotNull;
import org.liman.annotation.ForceFinal;
import org.liman.plugin.modifier.InspectionBundle;
import org.liman.plugin.modifier.ModifierListInspectionTool;
import org.liman.plugin.modifier.ModifierListQuickFix;

public class MarkFinal extends ModifierListInspectionTool<ForceFinal> {

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
                InspectionBundle.message("inspection.modifier.final.yes.description"),
                new ModifierListQuickFix(psiModifierList,
                        l -> l.setModifierProperty(PsiModifier.STATIC, true),
                        InspectionBundle.message("inspection.modifier.final.yes.name")));
    }
}


