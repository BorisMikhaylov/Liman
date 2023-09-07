// Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.

package org.liman.plugin.modifier;

import com.intellij.DynamicBundle;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.PropertyKey;

public final class LimanInspectionBundle extends DynamicBundle {

    private static final LimanInspectionBundle ourInstance = new LimanInspectionBundle();

    @NonNls
    public static final String BUNDLE = "messages.LimanInspectionBundle";

    private LimanInspectionBundle() {
        super(BUNDLE);
    }

    public static @Nls String message(@NotNull @PropertyKey(resourceBundle = BUNDLE) String key,
                                      Object... params) {
        return ourInstance.getMessage(key, params);
    }

}
