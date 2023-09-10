package org.liman.plugin.modifier;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.psi.PsiModifier;
import com.intellij.psi.PsiModifierList;
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
    public String getMessage() {
        return LimanInspectionBundle.message("inspection.description.make.final");
    }

    @Override
    public LocalQuickFix getQuickFix(PsiModifierList modifierList) {
        return new ModifierListQuickFix(modifierList,
                l -> l.setModifierProperty(PsiModifier.STATIC, true),
                LimanInspectionBundle.message("quickfix.make.final"));
    }
}


