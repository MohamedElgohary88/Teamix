package com.chocolate.usecases.member

import com.chocolate.entities.exceptions.InvalidEmailException
import com.chocolate.entities.exceptions.InvalidUsernameException
import com.chocolate.entities.exceptions.MissingRequiredFieldsException
import com.chocolate.entities.exceptions.PasswordMismatchException
import com.chocolate.entities.member.Member
import com.chocolate.entities.member.MemberInformation
import com.chocolate.entities.member.UserRole
import com.chocolate.entities.uills.Empty
import repositories.MemberRepository
import javax.inject.Inject

class CreateMemberUseCase @Inject constructor(
    private val memberRepository: MemberRepository,
) {

    suspend operator fun invoke(memberInfo: MemberInformation) {
        validateMemberInformation(memberInfo)
        isValidUsername(memberInfo.fullName)
        validateEmail(memberInfo.email)
        validatePassword(memberInfo.password, memberInfo.confirmPassword)

        val member = Member(
            id ="0",
            name = memberInfo.fullName,
            email = memberInfo.email,
            password = memberInfo.password,
            imageUrl = memberInfo.personalImageUri.toString(),
            isActive = true,
            UserRole.MEMBER,
            "your dream is so far far far far far",
            listOf("0")
        )

        memberRepository.createMember(member, memberInfo.password)

    }

    private fun validateMemberInformation(memberInfo: MemberInformation) {
        if (memberInfo.fullName.isBlank() ||
            memberInfo.email.isBlank() ||
            memberInfo.password.isBlank() ||
            memberInfo.confirmPassword.isBlank()
        ) throw MissingRequiredFieldsException(String.Empty)

    }

    private fun validatePassword(password: String, confirmPassword: String) {
        if (password != confirmPassword) {
            throw PasswordMismatchException(String.Empty)
        }
    }

    private fun isValidUsername(username: String) {
        val pattern = Regex("^[a-zA-Z][a-zA-Z0-9_ ]{2,}$")
        if (!pattern.matches(username)) throw InvalidUsernameException(String.Empty)
    }

    private fun validateEmail(email: String) {
        val emailRegex = Regex("""^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Z|a-z]{2,}$""")
        if (!emailRegex.matches(email)) throw InvalidEmailException(String.Empty)
    }
}
