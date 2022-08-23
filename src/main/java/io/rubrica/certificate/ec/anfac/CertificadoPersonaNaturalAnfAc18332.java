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
package io.rubrica.certificate.ec.anfac;

import io.rubrica.certificate.ec.CertificadoPersonaNatural;
import java.security.cert.X509Certificate;

/**
 * Certificado de persona natural emitido por ANf AC Ecuador
 *
 * @author mfernandez
 */
public class CertificadoPersonaNaturalAnfAc18332 extends CertificadoAnfAc18332 implements CertificadoPersonaNatural {

    public CertificadoPersonaNaturalAnfAc18332(X509Certificate certificado) {
        super(certificado);
    }
}
