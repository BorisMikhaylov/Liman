package org.liman.plugin.modifier.finall;

import com.intellij.codeInspection.LocalQuickFixOnPsiElement;
import com.intellij.codeInspection.util.IntentionFamilyName;
import com.intellij.codeInspection.util.IntentionName;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiModifier;
import com.intellij.psi.PsiModifierList;
import org.jetbrains.annotations.NotNull;

public class MarkMutableQuickFix extends LocalQuickFixOnPsiElement {

    public MarkMutableQuickFix(@NotNull PsiModifierList modifierList) {
        super(modifierList);
    }

    @Override
    public @IntentionName
    @NotNull String getText() {
        return "Make it mutable text";
    }

    @Override
    public @IntentionFamilyName
    @NotNull String getFamilyName() {
        return "Make it mutable family";
    }


    @Override
    public void invoke(@NotNull Project project, @NotNull PsiFile psiFile, @NotNull PsiElement psiElement, @NotNull PsiElement psiElement1) {
        PsiModifierList modifierListElement = (PsiModifierList) psiElement;
        modifierListElement.setModifierProperty(PsiModifier.FINAL, false);
    }
}
