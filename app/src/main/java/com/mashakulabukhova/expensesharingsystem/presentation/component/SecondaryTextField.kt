package com.mashakulabukhova.expensesharingsystem.presentation.component

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun SecondaryTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource? = null
){
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        label = label,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        prefix = prefix,
        suffix = suffix,
        supportingText = supportingText,
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        interactionSource = interactionSource,
        shape = RoundedCornerShape(20.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
            unfocusedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
            disabledTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
            errorTextColor = MaterialTheme.colorScheme.onPrimaryContainer,

            focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.primaryContainer,
            errorContainerColor = MaterialTheme.colorScheme.primaryContainer,

            cursorColor = MaterialTheme.colorScheme.primary,
            errorCursorColor = MaterialTheme.colorScheme.onError,
            selectionColors = TextSelectionColors(
                handleColor = MaterialTheme.colorScheme.primary,
                backgroundColor = MaterialTheme.colorScheme.background
            ),

            focusedBorderColor = MaterialTheme.colorScheme.primaryContainer,
            unfocusedBorderColor = MaterialTheme.colorScheme.primaryContainer,
            disabledBorderColor = MaterialTheme.colorScheme.primaryContainer,
            errorBorderColor = MaterialTheme.colorScheme.primaryContainer,

            focusedLeadingIconColor = MaterialTheme.colorScheme.surfaceTint,
            unfocusedLeadingIconColor = MaterialTheme.colorScheme.surfaceTint,
            disabledLeadingIconColor = MaterialTheme.colorScheme.surfaceTint,
            errorLeadingIconColor = MaterialTheme.colorScheme.surfaceTint,

            focusedTrailingIconColor = MaterialTheme.colorScheme.surfaceTint,
            unfocusedTrailingIconColor = MaterialTheme.colorScheme.surfaceTint,
            disabledTrailingIconColor = MaterialTheme.colorScheme.surfaceTint,
            errorTrailingIconColor = MaterialTheme.colorScheme.surfaceTint,

            focusedLabelColor = MaterialTheme.colorScheme.surfaceTint,
            unfocusedLabelColor = MaterialTheme.colorScheme.surfaceTint,
            disabledLabelColor = MaterialTheme.colorScheme.surfaceTint,
            errorLabelColor = MaterialTheme.colorScheme.surfaceTint,

            focusedPlaceholderColor = MaterialTheme.colorScheme.surfaceTint,
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.surfaceTint,
            disabledPlaceholderColor = MaterialTheme.colorScheme.surfaceTint,
            errorPlaceholderColor = MaterialTheme.colorScheme.surfaceTint,

            focusedSupportingTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedSupportingTextColor = MaterialTheme.colorScheme.onSurface,
            disabledSupportingTextColor = MaterialTheme.colorScheme.onSurface,
            errorSupportingTextColor = MaterialTheme.colorScheme.onSurface,

            focusedPrefixColor = MaterialTheme.colorScheme.onBackground,
            unfocusedPrefixColor = MaterialTheme.colorScheme.onBackground,
            disabledPrefixColor = MaterialTheme.colorScheme.onBackground,
            errorPrefixColor = MaterialTheme.colorScheme.onBackground,

            focusedSuffixColor = MaterialTheme.colorScheme.onBackground,
            unfocusedSuffixColor = MaterialTheme.colorScheme.onBackground,
            disabledSuffixColor = MaterialTheme.colorScheme.onBackground,
            errorSuffixColor = MaterialTheme.colorScheme.onBackground
        )

    )
}