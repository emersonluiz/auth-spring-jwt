package br.com.emersonluiz.mail;

import br.com.emersonluiz.model.User;

public class EmailRecover {

    private EmailRecover() {
        throw new AssertionError();
    }

    public static String recoverPassword(User user, String linkToken, String linkImg){

        String content = "<font face='Arial' size='2'>";

        content += " Caro " + user.getName() +
                   "<br /><p>Nosso sistema registrou uma solicitação de alteração de senha para \"LOGIN\" associado a esta conta de e-mail, " +
                   "caso o tenha solicitado favor clicar no link abaixo para efetivar a alteração. " +
                   "Lembrando que o link estará disponível por 24 horas, após este período ele expirará, " +
                   "ainda ressaltando que a senha deva ter os seguintes parâmetros: <br />" +
                   "Mínimo de 6 e máximo de 14 caracteres, havendo a obrigatoriedade de se usar, ao menos, " +
                   "uma letra Maiúscula e um caractere especial.</p>" +
                   "<p>" + linkToken + "</p>" +
                   "<br /><p>Não sendo sua a solicitação descarte a mensagem e informe-nos.</p>" +
                   "<br /><br /><p>Muito Obrigado</p>";

        content+=  "</br>\n" + 
                   "<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"416\">" +
                   "  <tr>" +
                   "    <td colspan=\"3\"><p><strong><em><img width=\"194\" height=\"50\" src=\""+linkImg+"\" /></em></strong></p></td>" +
                   "  </tr>" +
                   "</table>" +
                   "</font>";
//http://"+contextPath[2]+"/cums/imgs/structure/logo_email_2.png
        return content;
    }
}
