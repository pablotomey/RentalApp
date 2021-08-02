package cl.rentalea.rentalapp.utils

import android.annotation.SuppressLint
import cl.rentalea.rentalapp.utils.Constants.EMAIL
import cl.rentalea.rentalapp.utils.Constants.PASSWORD
import io.reactivex.Completable
import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

object Mailer {
    @SuppressLint("CheckResult")
    fun sendEmail(email: String, subject: String, msg: String): Completable {
        return Completable.create { emitter ->
            // Configuración de SMTP server
            val props: Properties = Properties().also {
                it["mail.smtp.host"] = "smtp.gmail.com"
                it["mail.smtp.socketFactory.port"] = "465"
                it["mail.smtp.socketFactory.class"] = "javax.net.ssl.SSLSocketFactory"
                it["mail.smtp.auth"] = "true"
                it["mail.smtp.port"] = "465"
            }

            // Crear sesion
            val session = Session.getDefaultInstance(props, object : Authenticator() {
                override fun getPasswordAuthentication(): PasswordAuthentication {
                    return PasswordAuthentication(EMAIL, PASSWORD)
                }
            })

            try {
                MimeMessage(session).let { mime ->
                    mime.setFrom(InternetAddress(EMAIL))
                    mime.addRecipient(Message.RecipientType.TO, InternetAddress(email))
                    mime.subject = subject
                    mime.setText(msg)
                    Transport.send(mime)
                }
            } catch (e: MessagingException) {
                emitter.onError(e)
            }

            emitter.onComplete()
        }
    }
}