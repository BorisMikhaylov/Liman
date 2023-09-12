package org.liman.plugin.modifier;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.psi.PsiModifier;
import com.intellij.psi.PsiModifierList;
import org.liman.annotation.ForceNonStatic;
import org.liman.plugin.LimanInspectionBundle;

public class EnforceNonStatic extends ModifierListInspectionTool<ForceNonStatic> {

    public EnforceNonStatic() {
        super(ForceNonStatic.class);
    }

    @Override
    public boolean checkModifier(PsiModifierList psiModifierList) {
        return !psiModifierList.hasModifierProperty(PsiModifier.STATIC);
    }

    @Override
    public String getMessage() {
        return LimanInspectionBundle.message("inspection.description.make.nonstatic");
    }

    @Override
    public LocalQuickFix getQuickFix(PsiModifierList modifierList) {
        return new ModifierListQuickFix(modifierList,
                l -> l.setModifierProperty(PsiModifier.STATIC, true),
                LimanInspectionBundle.message("quickfix.make.nonstatic"));
    }
}