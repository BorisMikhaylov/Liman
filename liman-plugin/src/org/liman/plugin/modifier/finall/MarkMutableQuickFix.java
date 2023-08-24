package org.liman.plugin.modifier.finall;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.codeInspection.util.IntentionFamilyName;
import com.intellij.codeInspection.util.IntentionName;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;

public class MarkMutableQuickFix implements LocalQuickFix {

    @SafeFieldForPreview
    private final SmartPsiElementPointer<PsiModifierList> modifierList;

    public MarkMutableQuickFix(@NotNull PsiModifierList modifierList) {
        PsiFile containingFile = modifierList.getContainingFile();
        Project project = containingFile == null ? modifierList.getProject() : containingFile.getProject();
        this.modifierList = SmartPointerManager.getInstance(project).createSmartPsiElementPointer(modifierList, containingFile);
    }

    @Override
    public @IntentionName
    @NotNull String getName() {
        return "Remove final modifier";
    }

    @Override
    public @IntentionFamilyName
    @NotNull String getFamilyName() {
        return "Remove final modifier";
    }

    @Override
    public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor problemDescriptor) {
        PsiModifierList modifierListElement = modifierList.getElement();
        if (modifierListElement == null) return;
        if (modifierListElement.hasModifierProperty(PsiModifier.FINAL)) {
            PsiAnnotation psiAnnotation = modifierListElement.findAnnotation(PsiModifier.FINAL);
            if (psiAnnotation != null) {
                psiAnnotation.delete();
            }
        }
    }
}
