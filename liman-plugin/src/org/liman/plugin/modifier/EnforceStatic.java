package org.liman.plugin.modifier;

import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiModifier;
import com.intellij.psi.PsiModifierList;
import org.jetbrains.annotations.NotNull;
import org.liman.annotation.ForceStatic;
import org.liman.plugin.LimanInspectionBundle;

public class EnforceStatic extends ModifierListInspectionTool<ForceStatic> {

    public EnforceStatic() {
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
                LimanInspectionBundle.message("inspection.description.make.static"),
                new ModifierListQuickFix(psiModifierList,
                        l -> l.setModifierProperty(PsiModifier.STATIC, true),
                        LimanInspectionBundle.message("quickfix.make.static")));
    }
}


