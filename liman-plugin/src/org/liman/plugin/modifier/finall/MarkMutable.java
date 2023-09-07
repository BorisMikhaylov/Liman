package org.liman.plugin.modifier.finall;

import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiModifier;
import com.intellij.psi.PsiModifierList;
import org.jetbrains.annotations.NotNull;
import org.liman.annotation.ForceMutable;
import org.liman.plugin.modifier.LimanInspectionBundle;
import org.liman.plugin.modifier.ModifierListInspectionTool;
import org.liman.plugin.modifier.ModifierListQuickFix;

public class MarkMutable extends ModifierListInspectionTool<ForceMutable> {

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
                LimanInspectionBundle.message("inspection.modifier.final.no.description"),
                new ModifierListQuickFix(psiModifierList,
                        l -> l.setModifierProperty(PsiModifier.STATIC, true),
                        LimanInspectionBundle.message("quick.fix.make.mutable")));
    }
}


