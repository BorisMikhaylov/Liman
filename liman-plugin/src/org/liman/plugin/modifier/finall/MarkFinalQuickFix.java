package org.liman.plugin.modifier.finall;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.codeInspection.util.IntentionFamilyName;
import com.intellij.codeInspection.util.IntentionName;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;

public class MarkFinalQuickFix implements LocalQuickFix {

    @SafeFieldForPreview
    private final SmartPsiElementPointer<PsiModifierList> modifierList;

    public MarkFinalQuickFix(@NotNull PsiModifierList modifierList) {
        PsiFile containingFile = modifierList.getContainingFile();
        Project project = containingFile == null ? modifierList.getProject() : containingFile.getProject();
        this.modifierList = SmartPointerManager.getInstance(project).createSmartPsiElementPointer(modifierList, containingFile);
    }

    @Override
    public @IntentionName
    @NotNull String getName() {
        return "Make it final";
    }

    @Override
    public @IntentionFamilyName
    @NotNull String getFamilyName() {
        return "Make it final";
    }

    @Override
    public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor problemDescriptor) {
        PsiModifierList modifierListElement = modifierList.getElement();
        if (modifierListElement == null) return;
        modifierListElement.setModifierProperty(PsiModifier.FINAL, true);
    }
}
