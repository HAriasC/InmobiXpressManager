package com.inmobixpress.inmobixpressmanager.ui.utils

import com.inmobixpress.inmobixpressmanager.R
import com.inmobixpress.inmobixpressmanager.ui.model.Account
import com.inmobixpress.inmobixpressmanager.ui.model.Email
import com.inmobixpress.inmobixpressmanager.ui.model.MailboxType

val allEmails = listOf(
    Email(
        id = 0L,
        sender = LocalAccountsDataProvider.getContactAccountByUid(9L),
        recipients = listOf(LocalAccountsDataProvider.getDefaultUserAccount()),
        subject = "Package shipped!",
        body = """
                Cucumber Mask Facial has shipped.

                Keep an eye out for a package to arrive between this Thursday and next Tuesday. If for any reason you don't receive your package before the end of next week, please reach out to us for details on your shipment.

                As always, thank you for shopping with us and we hope you love our specially formulated Cucumber Mask!
            """.trimIndent(),
        createdAt = "20 mins ago",
        isStarred = true,
    ),
    Email(
        id = 1L,
        sender = LocalAccountsDataProvider.getContactAccountByUid(6L),
        recipients = listOf(LocalAccountsDataProvider.getDefaultUserAccount()),
        subject = "Brunch this weekend?",
        body = """
                I'll be in your neighborhood doing errands and was hoping to catch you for a coffee this Saturday. If you don't have anything scheduled, it would be great to see you! It feels like its been forever.

                If we do get a chance to get together, remind me to tell you about Kim. She stopped over at the house to say hey to the kids and told me all about her trip to Mexico.

                Talk to you soon,

                Ali
            """.trimIndent(),
        createdAt = "40 mins ago",
    ),
    Email(
        2L,
        LocalAccountsDataProvider.getContactAccountByUid(5L),
        listOf(LocalAccountsDataProvider.getDefaultUserAccount()),
        "Bonjour from Paris",
        "Here are some great shots from my trip...",
        createdAt = "1 hour ago",
    ),
    Email(
        3L,
        LocalAccountsDataProvider.getContactAccountByUid(8L),
        listOf(LocalAccountsDataProvider.getDefaultUserAccount()),
        "High school reunion?",
        """
                Hi friends,

                I was at the grocery store on Sunday night.. when I ran into Genie Williams! I almost didn't recognize her afer 20 years!

                Anyway, it turns out she is on the organizing committee for the high school reunion this fall. I don't know if you were planning on going or not, but she could definitely use our help in trying to track down lots of missing alums. If you can make it, we're doing a little phone-tree party at her place next Saturday, hoping that if we can find one person, thee more will...
            """.trimIndent(),
        createdAt = "2 hours ago",
        mailbox = MailboxType.SENT,
    ),
    Email(
        id = 4L,
        sender = LocalAccountsDataProvider.getContactAccountByUid(11L),
        recipients = listOf(
            LocalAccountsDataProvider.getDefaultUserAccount(),
            LocalAccountsDataProvider.getContactAccountByUid(8L),
            LocalAccountsDataProvider.getContactAccountByUid(5L)
        ),
        subject = "Brazil trip",
        body = """
                Thought we might be able to go over some details about our upcoming vacation.

                I've been doing a bit of research and have come across a few paces in Northern Brazil that I think we should check out. One, the north has some of the most predictable wind on the planet. I'd love to get out on the ocean and kitesurf for a couple of days if we're going to be anywhere near or around Taiba. I hear it's beautiful there and if you're up for it, I'd love to go. Other than that, I haven't spent too much time looking into places along our road trip route. I'm assuming we can find places to stay and things to do as we drive and find places we think look interesting. But... I know you're more of a planner, so if you have ideas or places in mind, lets jot some ideas down!

                Maybe we can jump on the phone later today if you have a second.
            """.trimIndent(),
        createdAt = "2 hours ago",
        isStarred = true,
    ),
    Email(
        id = 5L,
        sender = LocalAccountsDataProvider.getContactAccountByUid(13L),
        recipients = listOf(LocalAccountsDataProvider.getDefaultUserAccount()),
        subject = "Update to Your Itinerary",
        body = "",
        createdAt = "2 hours ago",
    ),
    Email(
        id = 6L,
        sender = LocalAccountsDataProvider.getContactAccountByUid(10L),
        recipients = listOf(LocalAccountsDataProvider.getDefaultUserAccount()),
        subject = "Recipe to try",
        "Raspberry Pie: We should make this pie recipe tonight! The filling is " +
                "very quick to put together.",
        createdAt = "2 hours ago",
        mailbox = MailboxType.SENT,
    ),
    Email(
        id = 7L,
        sender = LocalAccountsDataProvider.getContactAccountByUid(9L),
        recipients = listOf(LocalAccountsDataProvider.getDefaultUserAccount()),
        subject = "Delivered",
        body = "Your shoes should be waiting for you at home!",
        createdAt = "2 hours ago",
    ),
    Email(
        id = 8L,
        sender = LocalAccountsDataProvider.getContactAccountByUid(13L),
        recipients = listOf(LocalAccountsDataProvider.getDefaultUserAccount()),
        subject = "Your update on Google Play Store is live!",
        body = """
              Your update, 0.1.1, is now live on the Play Store and available for your alpha users to start testing.
              
              Your alpha testers will be automatically notified. If you'd rather send them a link directly, go to your Google Play Console and follow the instructions for obtaining an open alpha testing link.
            """.trimIndent(),
        mailbox = MailboxType.TRASH,
        createdAt = "3 hours ago",
    ),
    Email(
        id = 9L,
        sender = LocalAccountsDataProvider.getContactAccountByUid(10L),
        recipients = listOf(LocalAccountsDataProvider.getDefaultUserAccount()),
        subject = "(No subject)",
        body = """
            Hey, 
            
            Wanted to email and see what you thought of
            """.trimIndent(),
        createdAt = "3 hours ago",
        mailbox = MailboxType.DRAFTS,
    ),
    Email(
        id = 10L,
        sender = LocalAccountsDataProvider.getContactAccountByUid(5L),
        recipients = listOf(LocalAccountsDataProvider.getDefaultUserAccount()),
        subject = "Try a free TrailGo account",
        body = """
            Looking for the best hiking trails in your area? TrailGo gets you on the path to the outdoors faster than you can pack a sandwich. 
            
            Whether you're an experienced hiker or just looking to get outside for the afternoon, there's a segment that suits you.
            """.trimIndent(),
        createdAt = "3 hours ago",
        mailbox = MailboxType.TRASH,
    ),
    Email(
        id = 11L,
        sender = LocalAccountsDataProvider.getContactAccountByUid(5L),
        recipients = listOf(LocalAccountsDataProvider.getDefaultUserAccount()),
        subject = "Free money",
        body = """
            You've been selected as a winner in our latest raffle! To claim your prize, click on the link.
            """.trimIndent(),
        createdAt = "3 hours ago",
        mailbox = MailboxType.SPAM,
    )
)

object LocalAccountsDataProvider {

    val allUserAccounts = listOf(
        Account(
            id = 1L,
            uid = 0L,
            firstName = "Jeff",
            lastName = "Hansen",
            email = "hikingfan@gmail.com",
            altEmail = "hkngfan@outside.com",
            avatar = R.drawable.ic_launcher_foreground,
            isCurrentAccount = true
        ),
        Account(
            id = 2L,
            uid = 0L,
            firstName = "Jeff",
            lastName = "H",
            email = "jeffersonloveshiking@gmail.com",
            altEmail = "jeffersonloveshiking@work.com",
            avatar = R.drawable.ic_launcher_foreground
        ),
        Account(
            id = 3L,
            uid = 0L,
            firstName = "Jeff",
            lastName = "Hansen",
            email = "jeffersonc@google.com",
            altEmail = "jeffersonc@gmail.com",
            avatar = R.drawable.ic_launcher_foreground
        )
    )

    private val allUserContactAccounts = listOf(
        Account(
            id = 4L,
            uid = 1L,
            firstName = "Tracy",
            lastName = "Alvarez",
            email = "tracealvie@gmail.com",
            altEmail = "tracealvie@gravity.com",
            avatar = R.drawable.ic_launcher_foreground
        ),
        Account(
            id = 5L,
            uid = 2L,
            firstName = "Allison",
            lastName = "Trabucco",
            email = "atrabucco222@gmail.com",
            altEmail = "atrabucco222@work.com",
            avatar = R.drawable.ic_launcher_foreground
        ),
        Account(
            id = 6L,
            uid = 3L,
            firstName = "Ali",
            lastName = "Connors",
            email = "aliconnors@gmail.com",
            altEmail = "aliconnors@android.com",
            avatar = R.drawable.ic_launcher_foreground
        ),
        Account(
            id = 7L,
            uid = 4L,
            firstName = "Alberto",
            lastName = "Williams",
            email = "albertowilliams124@gmail.com",
            altEmail = "albertowilliams124@chromeos.com",
            avatar = R.drawable.ic_launcher_foreground
        ),
        Account(
            id = 8L,
            uid = 5L,
            firstName = "Kim",
            lastName = "Alen",
            email = "alen13@gmail.com",
            altEmail = "alen13@mountainview.gov",
            avatar = R.drawable.ic_launcher_foreground
        ),
        Account(
            id = 9L,
            uid = 6L,
            firstName = "Google",
            lastName = "Express",
            email = "express@google.com",
            altEmail = "express@gmail.com",
            avatar = R.drawable.ic_launcher_foreground
        ),
        Account(
            id = 10L,
            uid = 7L,
            firstName = "Sandra",
            lastName = "Adams",
            email = "sandraadams@gmail.com",
            altEmail = "sandraadams@textera.com",
            avatar = R.drawable.ic_launcher_foreground
        ),
        Account(
            id = 11L,
            uid = 8L,
            firstName = "Trevor",
            lastName = "Hansen",
            email = "trevorhandsen@gmail.com",
            altEmail = "trevorhandsen@express.com",
            avatar = R.drawable.ic_launcher_foreground
        ),
        Account(
            id = 12L,
            uid = 9L,
            firstName = "Sean",
            lastName = "Holt",
            email = "sholt@gmail.com",
            altEmail = "sholt@art.com",
            avatar = R.drawable.ic_launcher_foreground
        ),
        Account(
            id = 13L,
            uid = 10L,
            firstName = "Frank",
            lastName = "Hawkins",
            email = "fhawkank@gmail.com",
            altEmail = "fhawkank@thisisme.com",
            avatar = R.drawable.ic_launcher_foreground
        )
    )

    /**
     * Get the current user's default account.
     */
    fun getDefaultUserAccount() = allUserAccounts.first()

    /**
     * Whether or not the given [Account.id] uid is an account owned by the current user.
     */
    fun isUserAccount(uid: Long): Boolean = allUserAccounts.any { it.uid == uid }

    /**
     * Get the contact of the current user with the given [accountId].
     */
    fun getContactAccountByUid(accountId: Long): Account {
        return allUserContactAccounts.first { it.id == accountId }
    }
}