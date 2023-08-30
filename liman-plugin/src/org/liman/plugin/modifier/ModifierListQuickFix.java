package org.liman.plugin.modifier;

import com.intellij.codeInspection.LocalQuickFixOnPsiElement;
import com.intellij.codeInspection.util.IntentionFamilyName;
import com.intellij.codeInspection.util.IntentionName;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiModifierList;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class ModifierListQuickFix extends LocalQuickFixOnPsiElement {

    @SafeFieldForPreview
    private final Consumer<PsiModifierList> consumer;
    private final @IntentionName String text;

    public ModifierListQuickFix(@NotNull PsiModifierList modifierList, Consumer<PsiModifierList> consumer, @IntentionName String text) {
        super(modifierList);
        this.consumer = consumer;
        this.text = text;
    }

    @Override
    @IntentionName
    public @NotNull String getText() {
        return text;
    }

    @Override
    public @IntentionFamilyName
    @NotNull String getFamilyName() {
        return InspectionBundle.message("inspection.liman.family.default");
    }

    @Override
    public void invoke(@NotNull Project project, @NotNull PsiFile psiFile, @NotNull PsiElement psiElement, @NotNull PsiElement psiElement1) {
        PsiModifierList modifierListElement = (PsiModifierList) psiElement;
        consumer.accept(modifierListElement);
    }
}
