/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ec.gob.firmadigital.cliente;

import io.rubrica.sign.SignConstants;
import io.rubrica.sign.Signer;
import io.rubrica.sign.pdf.PDFSigner;
import io.rubrica.sign.pdf.PdfUtil;
import io.rubrica.utils.FileUtils;
import io.rubrica.utils.TiempoUtils;
import io.rubrica.utils.Utils;
import java.io.File;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.util.Properties;

/**
 *
 * @author jdc
 */
public class FirmaDigital {

    public static byte[] firmar(
        KeyStore keyStore,
        String alias,
        File documento,
        char[] clave,
        int page,
        String razonFirma,
        String tipoFirma
    ) throws Exception {
        byte[] docByteArry = FileUtils.fileConvertToByteArray(documento);
        Signer signer = Utils.documentSigner(documento);

        // Version
        //  Properties config = PropertiesUtils.getConfig();
        // Propiedades para personalizar la firma
        Properties params = new Properties();
        params.setProperty(PDFSigner.SIGNING_LOCATION, "");
        params.setProperty(PDFSigner.SIGNING_REASON, razonFirma);
        params.setProperty(PDFSigner.SIGN_TIME, TiempoUtils.getFechaHoraServidor());

        if (tipoFirma != null && tipoFirma.equals("Firma Visible")) {
            // Posicion firma
            params.setProperty(PDFSigner.LAST_PAGE, String.valueOf(page));
            params.setProperty(PDFSigner.TYPE_SIG, "QR");
            params.setProperty(PDFSigner.INFO_QR, "VALIDAR CON: www.firmadigital.gob.ec\n" + "2.5.0-BETA-3");

            /*INFERIOR CENTRADO (ancho pie pagina)
            String llx = "100";
            String lly = "80";
            String urx = String.valueOf(Integer.parseInt(llx) + 430);
            String ury = String.valueOf(Integer.parseInt(lly) - 25);
            */

            //INFERIOR CENTRADO
            /* String llx = "260";
            String lly = "91";
            String urx = null;
            String ury = null;*/

            //CUSTOM
            String llx = "280";
            String lly = "280";
            String urx = null;
            String ury = null;

            params.setProperty(PdfUtil.POSITION_ON_PAGE_LOWER_LEFT_X, llx);
            params.setProperty(PdfUtil.POSITION_ON_PAGE_LOWER_LEFT_Y, lly);

            if (urx != null) {
                params.setProperty(PdfUtil.POSITION_ON_PAGE_UPPER_RIGHT_X, urx);
            }

            if (ury != null) {
                params.setProperty(PdfUtil.POSITION_ON_PAGE_UPPER_RIGHT_Y, ury);
            }
        }

        PrivateKey key = null;
        try {
            // Buscar el PrivateKey en el KeyStore:
            key = (PrivateKey) keyStore.getKey(alias, clave);
        } catch (java.security.UnrecoverableKeyException uke) {
            //certificado digital mal generado
            System.out.println(uke.getMessage());
        }

        // Obtener el certificate chain:
        Certificate[] certChain = keyStore.getCertificateChain(alias);

        if (key != null) {
            try {
                return signer.sign(docByteArry, SignConstants.SIGN_ALGORITHM_SHA1WITHRSA, key, certChain, params);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }
}
