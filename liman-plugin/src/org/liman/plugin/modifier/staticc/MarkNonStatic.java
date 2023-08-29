package org.liman.plugin.modifier.staticc;

import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiModifier;
import com.intellij.psi.PsiModifierList;
import org.jetbrains.annotations.NotNull;
import org.liman.annotation.ForceNonStatic;
import org.liman.plugin.modifier.ModifierListInspectionTool;

public class MarkNonStatic extends ModifierListInspectionTool<ForceNonStatic> {

    public MarkNonStatic() {
        super(ForceNonStatic.class);
    }

    @Override
    public boolean checkModifier(PsiModifierList psiModifierList) {
        return !psiModifierList.hasModifierProperty(PsiModifier.STATIC);
    }

    @Override
    public void registerProblem(@NotNull ProblemsHolder holder, @NotNull PsiAnnotation annotation, PsiModifierList psiModifierList) {
        holder.registerProblem(
                annotation,
                "Annotation target should not be static",
                new MarkNonStaticQuickFix(psiModifierList));
    }
}


