package com.azat.myretro.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Error {
	
	ERR0001("User not found."),
	ERR0002("You do not have permission to do this operation."),
	ERR0003("The User with given id not found."),
	ERR0004("User or token not valid"),
	ERR0005("Token expired"),
	ERR0006("Phone format must not be empty."),
	ERR0007("User ID must not be empty."),
	ERR0008("Name must not be empty."),
	ERR0009("Title/Description must not be empty."),
	ERR0010("Id must not be empty."),
	ERR0011("Name must not be empty."),
	ERR0012("Title must not be empty."),
	ERR0013("Id must not be empty."),
	ERR0014("Explanation must not be empty"),
	ERR0015("Fullname must not be empty."),
	ERR0016("Password must not be empty or less than 4 character"),
	ERR0017("Username must not be empty."),
	ERR0018("Username is already exists."),
	ERR0019("Phone must not be empty."),
	ERR0020("Phone number is already exists."),
	ERR0021("Firstname must not be empty."),
	ERR0022("Lastname must not be empty."),
	ERR0023("Password must not be empty or less than 4 character"),
	ERR0024("User status must not be empty"),
	ERR0025("Unknown error. Please contact us."),
	ERR0026("An unknown error has occurred when assigning role. Please contact us."),
	ERR0027("An unknown error has occurred when assigning new status. Please contact us."),
	ERR0028("Bad request."),
	ERR0029("UsernameOrPhone must not be empty."),
	ERR0030("Password must not be empty."),
	ERR0031("Wrong password"),
	ERR0032("TFA is not enabled"),
	ERR0033("User Role is already exists."),
	ERR0034("User Role not found"),
	ERR0035("Confirmation code must not be null or empty"),
	ERR0036("Your new password reset request just sent to your e-mail address."),
	ERR0037("User or token not valid"),
	ERR0038("Token expired"),
	ERR0039("Your password successfuly changed"),
	ERR0040("Your email successfuly verified"),
	ERR0041("Unknown token type"),
	ERR0042("Your email verification request just sent to your e-mail address."),
	ERR0043("Otp password sent."),
	ERR0044("OTP success"),
	ERR0045("Not found"),
	ERR0046("Item is already exist."),
	ERR0047("Name already exist, please choose a different value."),
	ERR0048("Already exist."),
	ERR0049("Is already exist."),
	ERR0050("Key already exist, please choose a different key."),
	ERR0051("Not found"),
	ERR0052("Current password must not be null or empty"),
	ERR0053("New password must not be null or empty"),
	ERR0054("New password validate must not be null or empty"),
	ERR0055("New password must not be less than 8 characters."),
	ERR0056("New passwords does not matches."),
	ERR0057("Wrong current password."),
	ERR0058("Password successfully changed."),
	ERR0059("Authenticated user could not be found."),
	ERR0060("You can only upload images while registartion."),
	ERR0061("A board with this name is already exist."),
	ERR0062("Board not found."),
	ERR0063("Author Name should not be empty."),
	ERR0064("Board Id should not be empty."),
	ERR0065("Column Id should not be empty.");
	
	private final String errorMessage;

	Error(final String error) {
		this.errorMessage = error;
	}
	@JsonValue
	public String getErrorMessage() {
		return errorMessage;
	}
}
