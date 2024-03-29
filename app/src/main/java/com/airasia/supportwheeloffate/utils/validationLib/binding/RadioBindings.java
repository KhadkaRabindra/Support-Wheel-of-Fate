package com.airasia.supportwheeloffate.utils.validationLib.binding;

import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.databinding.BindingAdapter;
import com.airasia.supportwheeloffate.R;
import com.airasia.supportwheeloffate.utils.validationLib.rule.RadioGroupSelectedRule;
import com.airasia.supportwheeloffate.utils.validationLib.util.ErrorMessageHelper;
import com.airasia.supportwheeloffate.utils.validationLib.util.RadioGroupHandler;
import com.airasia.supportwheeloffate.utils.validationLib.util.ViewTagHelper;

/**
 * Created by amanandhar on 2/5/18.
 */

public class RadioBindings {
    @BindingAdapter(value = {"validateRadioSelected", "validateRadioSelectedMessage", "validateRadioAutoDismiss"}, requireAll = false)
    public static void radioButtonSelected(RadioGroup view, TextView errorLabel, String errorMessage, boolean autoDismiss) {
        if (autoDismiss) {
            RadioGroupHandler.disableErrorOnChanged(view, errorLabel);
        }

        String handledErrorMessage = ErrorMessageHelper.getStringOrDefault(errorLabel,
                errorMessage, R.string.error_message_empty_validation);
        ViewTagHelper.appendValue(R.id.validator_rule, view, new RadioGroupSelectedRule(view, errorLabel, handledErrorMessage));
    }
}
