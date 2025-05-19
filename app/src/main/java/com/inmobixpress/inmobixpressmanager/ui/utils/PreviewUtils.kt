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
        subject = "Mensaje",
        body = """
                Estoy interesado en departamento ubicado en Calle Jorge Chavez 728, espero su pronta respuesta
            """.trimIndent(),
        createdAt = "Hace 20 minutos",
        isStarred = true,
    ),
    Email(
        id = 1L,
        sender = LocalAccountsDataProvider.getContactAccountByUid(6L),
        recipients = listOf(LocalAccountsDataProvider.getDefaultUserAccount()),
        subject = "Mensaje",
        body = """
                Consulta sobre disponibilidad del inmueble en Lima.
            """.trimIndent(),
        createdAt = "Hace 40 minutos",
    ),
    Email(
        2L,
        LocalAccountsDataProvider.getContactAccountByUid(5L),
        listOf(LocalAccountsDataProvider.getDefaultUserAccount()),
        "Mensaje",
        "Solicitud de visita para el inmueble en Miraflores.",
        createdAt = "Hace 1 hour atras",
    ),
    Email(
        3L,
        LocalAccountsDataProvider.getContactAccountByUid(8L),
        listOf(LocalAccountsDataProvider.getDefaultUserAccount()),
        "Mensaje",
        """
            Consulta sobre precio de alquiler en San Borja.
        """.trimIndent(),
        createdAt = "Hace 2 houras",
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
        subject = "Mensaje",
        body = """
                'Solicitud de visita para un inmueble en Barranco.'
            """.trimIndent(),
        createdAt = "Ayer",
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
            firstName = "Juan",
            lastName = "Alvarez",
            email = "tracealvie@gmail.com",
            altEmail = "tracealvie@gravity.com",
            avatar = R.drawable.ic_launcher_foreground
        ),
        Account(
            id = 5L,
            uid = 2L,
            firstName = "Maria",
            lastName = "Trabucco",
            email = "atrabucco222@gmail.com",
            altEmail = "atrabucco222@work.com",
            avatar = R.drawable.ic_launcher_foreground
        ),
        Account(
            id = 6L,
            uid = 3L,
            firstName = "Carlos",
            lastName = "Connors",
            email = "aliconnors@gmail.com",
            altEmail = "aliconnors@android.com",
            avatar = R.drawable.ic_launcher_foreground
        ),
        Account(
            id = 7L,
            uid = 4L,
            firstName = "Arianne",
            lastName = "Williams",
            email = "albertowilliams124@gmail.com",
            altEmail = "albertowilliams124@chromeos.com",
            avatar = R.drawable.ic_launcher_foreground
        ),
        Account(
            id = 8L,
            uid = 5L,
            firstName = "Luis",
            lastName = "Alen",
            email = "alen13@gmail.com",
            altEmail = "alen13@mountainview.gov",
            avatar = R.drawable.ic_launcher_foreground
        ),
        Account(
            id = 9L,
            uid = 6L,
            firstName = "Lucia",
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
            firstName = "Juan",
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