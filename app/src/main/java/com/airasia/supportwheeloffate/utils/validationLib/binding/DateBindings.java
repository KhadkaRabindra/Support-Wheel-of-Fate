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
import com.airasia.supportwheeloffate.utils.validationLib.rule.DateRule;
import com.airasia.supportwheeloffate.utils.validationLib.util.EditTextHandler;
import com.airasia.supportwheeloffate.utils.validationLib.util.ErrorMessageHelper;
import com.airasia.supportwheeloffate.utils.validationLib.util.ViewTagHelper;


/**
 * Created by john-mac on 6/16/16.
 */
public class DateBindings {

    @BindingAdapter(value = {"validateDate", "validateDateMessage", "validateDateAutoDismiss"}, requireAll = false)
    public static void bindingDate(TextView view, String pattern, String errorMessage, boolean autoDismiss) {
        if (autoDismiss) {
            EditTextHandler.disableErrorOnChanged(view);
        }

        String handledErrorMessage = ErrorMessageHelper.getStringOrDefault(view,
                errorMessage, R.string.error_message_date_validation);
        ViewTagHelper.appendValue(R.id.validator_rule, view, new DateRule(view, pattern, handledErrorMessage));
    }

}
