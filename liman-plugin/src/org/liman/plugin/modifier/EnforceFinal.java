package org.liman.plugin.modifier;

import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiModifier;
import com.intellij.psi.PsiModifierList;
import org.jetbrains.annotations.NotNull;
import org.liman.annotation.ForceFinal;
import org.liman.plugin.LimanInspectionBundle;

public class EnforceFinal extends ModifierListInspectionTool<ForceFinal> {

    public EnforceFinal() {
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
                LimanInspectionBundle.message("inspection.description.make.final"),
                new ModifierListQuickFix(psiModifierList,
                        l -> l.setModifierProperty(PsiModifier.STATIC, true),
                        LimanInspectionBundle.message("quickfix.make.final")));
    }
}


