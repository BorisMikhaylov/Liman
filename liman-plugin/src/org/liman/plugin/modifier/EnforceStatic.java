package org.liman.plugin.modifier;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.psi.PsiModifier;
import com.intellij.psi.PsiModifierList;
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
    public String getMessage() {
        return LimanInspectionBundle.message("inspection.description.make.static");
    }

    @Override
    public LocalQuickFix getQuickFix(PsiModifierList modifierList) {
        return new ModifierListQuickFix(modifierList,
                l -> l.setModifierProperty(PsiModifier.STATIC, true),
                LimanInspectionBundle.message("quickfix.make.static"));
    }
}


