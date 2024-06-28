package meloncommands.config;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import net.minecraft.core.net.command.TextFormatting;

import java.util.ArrayList;
import java.util.List;

public class RoleData {

	//Role Display Formatting

	@SerializedName(value = "Role Display: ")
	@Expose public String roleDisplay = "" + TextFormatting.RESET;

		//Display General

		@SerializedName(value = "Role Display Color:")
		@Expose public String roleDisplayColor = "";

		@SerializedName(value = "Role Display Underlined:")
		@Expose public boolean isRoleDisplayUnderlined = false;

		@SerializedName(value = "Role Display Bold:")
		@Expose public boolean isRoleDisplayBold = false;

		@SerializedName(value = "Role Display Italics:")
		@Expose public boolean isRoleDisplayItalics = false;

		//Display Border

		@SerializedName(value = "Bracket Border:")
		@Expose public boolean isBracketBorder = true;

		@SerializedName(value = "Curly Bracket Border:")
		@Expose public boolean isCurlyBracketBorder = false;

		@SerializedName(value = "Caret Border:")
		@Expose public boolean isCaretBorder = false;

		@SerializedName(value = "Custom Border:")
		@Expose public boolean isCustomBorder = false;

			@SerializedName(value = "Border Prefix:")
			@Expose public String borderPrefix = "";

			@SerializedName(value = "Border Suffix:")
			@Expose public String borderSuffix = "";

	//Role Text Formatting

	@SerializedName(value = "Role Text Formatting:")
	@Expose public String roleTextFormatting = "";

		@SerializedName(value = "Role Text Color:")
		@Expose public String roleTextColor = "";

		@SerializedName(value = "Role Text Underlined:")
		@Expose public boolean isRoleTextUnderlined = false;

		@SerializedName(value = "Role Text Bold:")
		@Expose public boolean isRoleTextBold = false;

		@SerializedName(value = "Role Text Italics:")
		@Expose public boolean isRoleTextItalics = false;

	//Players Granted Role

	@SerializedName(value = "Players Granted Role:")
	@Expose public List<String> playersGrantedRole = new ArrayList<>();

}
