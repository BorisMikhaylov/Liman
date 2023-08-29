package org.liman.plugin.modifier;

import com.intellij.codeInspection.LocalQuickFixOnPsiElement;
import com.intellij.codeInspection.util.IntentionFamilyName;
import com.intellij.codeInspection.util.IntentionName;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiModifierList;
import org.jetbrains.annotations.NotNull;

public abstract class QuickFix extends LocalQuickFixOnPsiElement {

    private final String modifier;
    private final boolean isStraight;

    public QuickFix(@NotNull PsiModifierList modifierList, String modifier, boolean isStraight) {
        super(modifierList);
        this.modifier = modifier;
        this.isStraight = isStraight;
    }

    @Override
    public @IntentionName
    @NotNull String getText() {
        return "Make it " + modifier + " text";
    }

    @Override
    public @IntentionFamilyName
    @NotNull String getFamilyName() {
        return "Make it " + modifier + " family";
    }

    @Override
    public void invoke(@NotNull Project project, @NotNull PsiFile psiFile, @NotNull PsiElement psiElement, @NotNull PsiElement psiElement1) {
        PsiModifierList modifierListElement = (PsiModifierList) psiElement;
        modifierListElement.setModifierProperty(modifier, isStraight);
    }
}
