package meloncommands.config;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class RoleData {

	//Role Display Formatting

		//Display General

		@SerializedName(value = "Role Display Color:")
		@Expose public String displayColor = "white";

		@SerializedName(value = "Role Display Name:")
		@Expose public String displayName = "DefaultRoleName";

		@SerializedName(value = "Role Display Underlined:")
		@Expose public boolean isDisplayUnderlined = false;

		@SerializedName(value = "Role Display Bold:")
		@Expose public boolean isDisplayBold = false;

		@SerializedName(value = "Role Display Italics:")
		@Expose public boolean isDisplayItalics = false;

		//Display Border

		@SerializedName(value = "Bracket Border Color:")
		@Expose public String displayBorderColor = "white";

		@SerializedName(value = "Bracket Border:")
		@Expose public boolean isDisplayBracketBorder = true;

		@SerializedName(value = "Curly Bracket Border:")
		@Expose public boolean isDisplayCurlyBracketBorder = false;

		@SerializedName(value = "Caret Border:")
		@Expose public boolean isDisplayCaretBorder = false;

		@SerializedName(value = "Custom Border:")
		@Expose public boolean isDisplayCustomBorder = false;

		@SerializedName(value = "Custom Border Prefix:")
		@Expose public String customBorderPrefix = "";

		@SerializedName(value = "Custom Border Suffix:")
		@Expose public String customBorderSuffix = "";

	//Role Text Formatting

		@SerializedName(value = "Role Text Color:")
		@Expose public String textColor = "§0";

		@SerializedName(value = "Role Text Underlined:")
		@Expose public boolean isTextUnderlined = false;

		@SerializedName(value = "Role Text Bold:")
		@Expose public boolean isTextBold = false;

		@SerializedName(value = "Role Text Italics:")
		@Expose public boolean isTextItalics = false;

	//Players Granted Role

		@SerializedName(value = "Players Granted Role:")
		@Expose public List<String> playersGrantedRole = new ArrayList<>();

		@SerializedName(value = "Role Priority: (Highest - 0..1..2.. - Lowest)")
		@Expose public int priority = 0;

}
