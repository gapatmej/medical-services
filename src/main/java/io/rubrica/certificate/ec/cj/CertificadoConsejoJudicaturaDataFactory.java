/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.rubrica.certificate.ec.cj;

import static io.rubrica.certificate.ec.cj.CertificadoConsejoJudicatura.*;
import static io.rubrica.utils.BouncyCastleUtils.certificateHasPolicy;

import java.security.cert.X509Certificate;

/**
 * Permite construir certificados tipo CertificadoConsejoJudicatura a partir de
 * certificados X509Certificate.
 *
 * @author mfernandez
 */
public class CertificadoConsejoJudicaturaDataFactory {

    public static boolean esCertificadoDelConsejoJudicatura(X509Certificate certificado) {
        return (
            certificateHasPolicy(certificado, OID_CERTIFICADO_PERSONA_NATURAL) ||
            certificateHasPolicy(certificado, OID_CERTIFICADO_PERSONA_JURIDICA_PRIVADA) ||
            certificateHasPolicy(certificado, OID_CERTIFICADO_PERSONA_JURIDICA_PUBLICA) ||
            certificateHasPolicy(certificado, OID_CERTIFICADO_MIEMBRO_EMPRESA) ||
            certificateHasPolicy(certificado, OID_CERTIFICADO_EMPRESA) ||
            certificateHasPolicy(certificado, OID_CERTIFICADO_DEPARTAMENTO_EMPRESA)
        );
    }

    public static CertificadoConsejoJudicatura construir(X509Certificate certificado) {
        if (certificateHasPolicy(certificado, OID_CERTIFICADO_PERSONA_NATURAL)) {
            return new CertificadoPersonaNaturalConsejoJudicatura(certificado);
        } else if (certificateHasPolicy(certificado, OID_CERTIFICADO_PERSONA_JURIDICA_PRIVADA)) {
            return new CertificadoPersonaJuridicaPrivadaConsejoJudicatura(certificado);
        } else if (certificateHasPolicy(certificado, OID_CERTIFICADO_PERSONA_JURIDICA_PUBLICA)) {
            return new CertificadoPersonaJuridicaPublicaConsejoJudicatura(certificado);
        } else if (certificateHasPolicy(certificado, OID_CERTIFICADO_MIEMBRO_EMPRESA)) {
            return new CertificadoMiembroEmpresaConsejoJudicatura(certificado);
        } else if (certificateHasPolicy(certificado, OID_CERTIFICADO_EMPRESA)) {
            return new CertificadoEmpresaConsejoJudicatura(certificado);
        } else if (certificateHasPolicy(certificado, OID_CERTIFICADO_DEPARTAMENTO_EMPRESA)) {
            return new CertificadoDepartamentoEmpresaConsejoJudicatura(certificado);
        } else {
            throw new RuntimeException("Certificado del Consejo de la Judicatura de tipo desconocido!");
        }
    }
}
