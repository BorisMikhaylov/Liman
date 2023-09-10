package org.liman.plugin.modifier;

import com.intellij.codeInspection.AbstractBaseJavaLocalInspectionTool;
import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;
import org.liman.MessageLevel;
import org.liman.annotation.LimanMessageMaxLevel;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import static org.liman.MessageLevel.ERROR;
import static org.liman.MessageLevel.values;

public abstract class ModifierListInspectionTool<A extends Annotation> extends AbstractBaseJavaLocalInspectionTool {
    private final Class<A> clazz;

    public ModifierListInspectionTool(Class<A> clazz) {
        this.clazz = clazz;
    }

    @Override
    public @NotNull PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
        return new JavaElementVisitor() {
            @Override
            public void visitAnnotation(@NotNull PsiAnnotation annotation) {
                super.visitAnnotation(annotation);
                boolean value = Optional.of(annotation)
                        .map(PsiAnnotation::resolveAnnotationType)
                        .map(a -> a.getAnnotation(clazz.getCanonicalName()))
                        .isPresent();
                if (!value) {
                    return;
                }
                PsiModifierList psiModifierList = Optional.of(annotation)
                        .map(PsiAnnotation::getParent)
                        .filter(PsiModifierList.class::isInstance)
                        .map(PsiModifierList.class::cast)
                        .orElse(null);
                if (psiModifierList == null) {
                    return;
                }
                if (checkModifier(psiModifierList)) {
                    return;
                }
                MessageLevel messageLevel = getOverloadingMaxLevel(annotation.getParent().getParent()).orElse(ERROR);
                registerProblem(holder, annotation, psiModifierList, messageLevel);
            }
        };
    }

    public abstract boolean checkModifier(PsiModifierList psiModifierList);

    public abstract String getMessage();

    public abstract LocalQuickFix getQuickFix(PsiModifierList modifierList);

    public void registerProblem(@NotNull ProblemsHolder holder, @NotNull PsiAnnotation annotation, PsiModifierList psiModifierList, MessageLevel messageLevel) {
        holder.registerProblem(
                annotation,
                getMessage(),
                getProblemHighlightTypeFromMessageLevel(messageLevel),
                getQuickFix(psiModifierList));
    }

    private Optional<MessageLevel> getOverloadingMaxLevel(PsiElement element) {
        if (element == null) {
            return Optional.empty();
        }
        Optional<PsiAnnotationMemberValue> value = Optional.of(element)
                .filter(PsiModifierListOwner.class::isInstance)
                .map(PsiModifierListOwner.class::cast)
                .map(PsiModifierListOwner::getModifierList)
                .map(PsiModifierList::getAnnotations)
                .stream()
                .flatMap(Arrays::stream)
                .filter(a -> a.hasQualifiedName(LimanMessageMaxLevel.class.getCanonicalName()))
                .map(a -> a.findAttributeValue("value"))
                .filter(Objects::nonNull)
                .findFirst();
        return value.map(ModifierListInspectionTool::getMessageLevelFromPsiAnnotationValue).or(() -> getOverloadingMaxLevel(element.getParent()));
    }

    public static MessageLevel getMessageLevelFromPsiAnnotationValue(PsiAnnotationMemberValue value) {
        if (!(value instanceof PsiReferenceExpression)) return null;
        PsiReferenceExpression expr = (PsiReferenceExpression) value;
        PsiType type = expr.getType();
        if (type == null) return null;
        if (!MessageLevel.class.getCanonicalName().equals(type.getCanonicalText())) return null;
        String text = value.getText();
        for (MessageLevel messageLevel : values()) {
            if (text.endsWith(messageLevel.toString())) return messageLevel;
        }
        return null;
    }

    public static ProblemHighlightType getProblemHighlightTypeFromMessageLevel(MessageLevel messageLevel) {
        return switch (messageLevel) {
            case ERROR -> ProblemHighlightType.GENERIC_ERROR;
            case WARNING -> ProblemHighlightType.WARNING;
            case INFO -> ProblemHighlightType.INFORMATION;
        };
    }
}
