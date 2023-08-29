package org.liman.plugin.modifier.staticc;

import com.intellij.psi.PsiModifier;
import com.intellij.psi.PsiModifierList;
import org.jetbrains.annotations.NotNull;
import org.liman.plugin.modifier.QuickFix;

public class MarkNonStaticQuickFix extends QuickFix {

    public MarkNonStaticQuickFix(@NotNull PsiModifierList modifierList) {
        super(modifierList, PsiModifier.STATIC, false);
    }
}
