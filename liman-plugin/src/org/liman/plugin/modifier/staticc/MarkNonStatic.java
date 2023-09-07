package org.liman.plugin.modifier.staticc;

import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiModifier;
import com.intellij.psi.PsiModifierList;
import org.jetbrains.annotations.NotNull;
import org.liman.annotation.ForceNonStatic;
import org.liman.plugin.modifier.LimanInspectionBundle;
import org.liman.plugin.modifier.ModifierListInspectionTool;
import org.liman.plugin.modifier.ModifierListQuickFix;

public class MarkNonStatic extends ModifierListInspectionTool<ForceNonStatic> {

    public MarkNonStatic() {
        super(ForceNonStatic.class);
    }

    @Override
    public boolean checkModifier(PsiModifierList psiModifierList) {
        return !psiModifierList.hasModifierProperty(PsiModifier.STATIC);
    }

    @Override
    public void registerProblem(@NotNull ProblemsHolder holder, @NotNull PsiAnnotation annotation, PsiModifierList psiModifierList) {
        holder.registerProblem(
                annotation,
                LimanInspectionBundle.message("inspection.modifier.static.no.description"),
                new ModifierListQuickFix(psiModifierList,
                        l -> l.setModifierProperty(PsiModifier.STATIC, true),
                        LimanInspectionBundle.message("quick.fix.make.nonstatic")));
    }
}


