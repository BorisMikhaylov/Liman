package org.liman.plugin.modifier;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.psi.PsiModifier;
import com.intellij.psi.PsiModifierList;
import org.liman.annotation.ForceMutable;
import org.liman.plugin.LimanInspectionBundle;

public class EnforceMutable extends ModifierListInspectionTool<ForceMutable> {

    public EnforceMutable() {
        super(ForceMutable.class);
    }

    @Override
    public boolean checkModifier(PsiModifierList psiModifierList) {
        return !psiModifierList.hasModifierProperty(PsiModifier.FINAL);
    }


    @Override
    public String getMessage() {
        return LimanInspectionBundle.message("inspection.description.make.mutable");
    }

    @Override
    public LocalQuickFix getQuickFix(PsiModifierList modifierList) {
        return new ModifierListQuickFix(modifierList,
                l -> l.setModifierProperty(PsiModifier.STATIC, true),
                LimanInspectionBundle.message("quickfix.make.mutable"));
    }
}


