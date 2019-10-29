/*
 * Copyright (c) 2017-present Ilhasoft.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.airasia.supportwheeloffate.utils.validationLib.binding;

import android.widget.TextView;
import androidx.databinding.BindingAdapter;
import com.airasia.supportwheeloffate.R;
import com.airasia.supportwheeloffate.utils.validationLib.rule.ConfirmPasswordRule;
import com.airasia.supportwheeloffate.utils.validationLib.util.EditTextHandler;
import com.airasia.supportwheeloffate.utils.validationLib.util.ErrorMessageHelper;
import com.airasia.supportwheeloffate.utils.validationLib.util.ViewTagHelper;

/**
 * Created by felipe on 22/12/16.
 */
public class PasswordBindings {

    @BindingAdapter(value = {"validatePassword", "validatePasswordMessage", "validatePasswordAutoDismiss"}, requireAll = false)
    public static void bindingPassword(TextView view, TextView comparableView, String errorMessage, boolean autoDismiss) {
        if (autoDismiss) {
            EditTextHandler.disableErrorOnChanged(view);
        }

        String handledErrorMessage = ErrorMessageHelper.getStringOrDefault(view,
                errorMessage, R.string.error_message_not_equal_password);
        ViewTagHelper.appendValue(R.id.validator_rule, view,
                new ConfirmPasswordRule(view, comparableView, handledErrorMessage));
    }

}
