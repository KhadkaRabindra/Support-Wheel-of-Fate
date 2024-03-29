/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.airasia.supportwheeloffate.utils.validationLib.routines;

import com.airasia.supportwheeloffate.utils.validationLib.routines.checkdigit.CheckDigit;
import com.airasia.supportwheeloffate.utils.validationLib.routines.checkdigit.LuhnCheckDigit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Perform credit card validations.
 * <p>
 * <p>
 * By default, all supported card types are allowed.  You can specify which
 * cards should pass validation by configuring the validation options. For
 * example,
 * </p>
 * <p>
 * <pre>
 * <code>CreditCardValidator ccv = new CreditCardValidator(CreditCardValidator.AMEX + CreditCardValidator.VISA);</code>
 * </pre>
 * <p>
 * <p>
 * configures the validator to only pass American Express and Visa cards.
 * If a card type is not directly supported by this class, you can implement
 * the CreditCardType interface and pass an instance into the
 * <code>addAllowedCardType</code> method.
 * </p>
 * <p>
 * <p>
 * For a similar implementation in Perl, reference Sean M. Burke's
 * <a href="http://www.speech.cs.cmu.edu/~sburke/pub/luhn_lib.html">script</a>.
 * More information can be found in Michael Gilleland's essay
 * <a href="http://web.archive.org/web/20120614072656/http://www.merriampark.com/anatomycc.htm">Anatomy of Credit Card Numbers</a>.
 * </p>
 *
 * @version $Revision: 1739207 $
 * @since Validator 1.4
 */
public class CreditCardValidator implements Serializable {

    private static final long serialVersionUID = 5955978921148959496L;

    /**
     * Option specifying that no cards are allowed.  This is useful if
     * you want only custom card types to validate so you turn off the
     * default cards with this option.
     * <p>
     * <pre>
     * <code>
     * CreditCardValidator v = new CreditCardValidator(CreditCardValidator.NONE);
     * v.addAllowedCardType(customType);
     * v.isValid(aCardNumber);
     * </code>
     * </pre>
     */
    public static final long NONE = 0;

    /**
     * Option specifying that American Express cards are allowed.
     */
    public static final long AMEX = 1 << 0;

    /**
     * Option specifying that Visa cards are allowed.
     */
    public static final long VISA = 1 << 1;

    /**
     * Option specifying that Mastercard cards are allowed.
     */
    public static final long MASTERCARD = 1 << 2;

    /**
     * Option specifying that Discover cards are allowed.
     */
    public static final long DISCOVER = 1 << 3; // CHECKSTYLE IGNORE MagicNumber

    /**
     * Option specifying that Diners cards are allowed.
     */
    public static final long DINERS = 1 << 4; // CHECKSTYLE IGNORE MagicNumber

    /**
     * Option specifying that VPay (Visa) cards are allowed.
     *
     * @since 1.5.0
     */
    public static final long VPAY = 1 << 5; // CHECKSTYLE IGNORE MagicNumber

    /**
     * Option specifying that Mastercard cards (pre Oct 2016 only) are allowed.
     *
     * @deprecated for use until Oct 2016 only
     */
    @Deprecated
    public static final long MASTERCARD_PRE_OCT2016 = 1 << 6; // CHECKSTYLE IGNORE MagicNumber


    /**
     * The CreditCardTypes that are allowed to pass validation.
     */
    private final List<CodeValidator> cardTypes = new ArrayList<CodeValidator>();

    /**
     * Luhn checkdigit validator for the card numbers.
     */
    private static final CheckDigit LUHN_VALIDATOR = LuhnCheckDigit.LUHN_CHECK_DIGIT;

    /**
     * American Express (Amex) Card Validator
     */
    public static final CodeValidator AMEX_VALIDATOR = new CodeValidator("^(3[47]\\d{13})$", LUHN_VALIDATOR);

    /**
     * Diners Card Validator
     */
    public static final CodeValidator DINERS_VALIDATOR = new CodeValidator("^(30[0-5]\\d{11}|3095\\d{10}|36\\d{12}|3[8-9]\\d{12})$", LUHN_VALIDATOR);

    /**
     * Discover Card regular expressions
     */
    private static final RegexValidator DISCOVER_REGEX = new RegexValidator(new String[]{"^(6011\\d{12})$", "^(64[4-9]\\d{13})$", "^(65\\d{14})$"});

    /**
     * Discover Card Validator
     */
    public static final CodeValidator DISCOVER_VALIDATOR = new CodeValidator(DISCOVER_REGEX, LUHN_VALIDATOR);

    /**
     * Mastercard regular expressions
     */
    private static final RegexValidator MASTERCARD_REGEX = new RegexValidator(
            new String[]{
                    "^(5[1-5]\\d{14})$",  // 51 - 55 (pre Oct 2016)
                    // valid from October 2016
                    "^(2221\\d{12})$",    // 222100 - 222199
                    "^(222[2-9]\\d{12})$",// 222200 - 222999
                    "^(22[3-9]\\d{13})$", // 223000 - 229999
                    "^(2[3-6]\\d{14})$",  // 230000 - 269999
                    "^(27[01]\\d{13})$",  // 270000 - 271999
                    "^(2720\\d{12})$",    // 272000 - 272099
            });

    /**
     * Mastercard Card Validator
     */
    public static final CodeValidator MASTERCARD_VALIDATOR = new CodeValidator(MASTERCARD_REGEX, LUHN_VALIDATOR);

    /**
     * Mastercard Card Validator (pre Oct 2016)
     *
     * @deprecated for use until Oct 2016 only
     */
    @Deprecated
    public static final CodeValidator MASTERCARD_VALIDATOR_PRE_OCT2016 = new CodeValidator("^(5[1-5]\\d{14})$", LUHN_VALIDATOR);

    /**
     * Visa Card Validator
     */
    public static final CodeValidator VISA_VALIDATOR = new CodeValidator("^(4)(\\d{12}|\\d{15})$", LUHN_VALIDATOR);

    /**
     * VPay (Visa) Card Validator
     *
     * @since 1.5.0
     */
    public static final CodeValidator VPAY_VALIDATOR = new CodeValidator("^(4)(\\d{12,18})$", LUHN_VALIDATOR);

    /**
     * Create a new CreditCardValidator with default options.
     * The default options are:
     * AMEX, VISA, MASTERCARD and DISCOVER
     */
    public CreditCardValidator() {
        this(AMEX + VISA + MASTERCARD + DISCOVER);
    }

    /**
     * Create a new CreditCardValidator with the specified options.
     *
     * @param options Pass in
     *                CreditCardValidator.VISA + CreditCardValidator.AMEX to specify that
     *                those are the only valid card types.
     */
    public CreditCardValidator(long options) {
        super();

        if (isOn(options, VISA)) {
            this.cardTypes.add(VISA_VALIDATOR);
        }

        if (isOn(options, VPAY)) {
            this.cardTypes.add(VPAY_VALIDATOR);
        }

        if (isOn(options, AMEX)) {
            this.cardTypes.add(AMEX_VALIDATOR);
        }

        if (isOn(options, MASTERCARD)) {
            this.cardTypes.add(MASTERCARD_VALIDATOR);
        }

        if (isOn(options, MASTERCARD_PRE_OCT2016)) {
            this.cardTypes.add(MASTERCARD_VALIDATOR_PRE_OCT2016);
        }

        if (isOn(options, DISCOVER)) {
            this.cardTypes.add(DISCOVER_VALIDATOR);
        }

        if (isOn(options, DINERS)) {
            this.cardTypes.add(DINERS_VALIDATOR);
        }
    }

    /**
     * Create a new CreditCardValidator with the specified {@link  CodeValidator}s.
     *
     * @param creditCardValidators Set of valid code validators
     */
    public CreditCardValidator(CodeValidator[] creditCardValidators) {
        if (creditCardValidators == null) {
            throw new IllegalArgumentException("Card validators are missing");
        }
        Collections.addAll(cardTypes, creditCardValidators);
    }

    /**
     * Checks if the field is a valid credit card number.
     *
     * @param card The card number to validate.
     * @return Whether the card number is valid.
     */
    public boolean isValid(String card) {
        if (card == null || card.length() == 0) {
            return false;
        }
        for (CodeValidator cardType : cardTypes) {
            if (cardType.isValid(card)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the field is a valid credit card number.
     *
     * @param card The card number to validate.
     * @return The card number if valid or <code>null</code>
     * if invalid.
     */
    public Object validate(String card) {
        if (card == null || card.length() == 0) {
            return null;
        }
        Object result = null;
        for (CodeValidator cardType : cardTypes) {
            result = cardType.validate(card);
            if (result != null) {
                return result;
            }
        }
        return null;

    }

    /**
     * Tests whether the given flag is on.  If the flag is not a power of 2
     * (ie. 3) this tests whether the combination of flags is on.
     *
     * @param options The options specified.
     * @param flag    Flag value to check.
     * @return whether the specified flag value is on.
     */
    private boolean isOn(long options, long flag) {
        return (options & flag) > 0;
    }

}
