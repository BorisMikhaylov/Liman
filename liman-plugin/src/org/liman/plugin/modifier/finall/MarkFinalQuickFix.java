package org.liman.plugin.modifier.finall;

import com.intellij.psi.PsiModifier;
import com.intellij.psi.PsiModifierList;
import org.jetbrains.annotations.NotNull;
import org.liman.plugin.modifier.QuickFix;

public class MarkFinalQuickFix extends QuickFix {

    public MarkFinalQuickFix(@NotNull PsiModifierList modifierList) {
        super(modifierList, PsiModifier.FINAL, true);
    }
}
