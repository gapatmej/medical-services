package com.customsoftware.medicalservices.service.impl;

import com.customsoftware.medicalservices.service.POCSignService;
import ec.gob.firmadigital.cliente.FirmaDigital;
import io.rubrica.certificate.CertEcUtils;
import io.rubrica.certificate.CertUtils;
import io.rubrica.keystore.FileKeyStoreProvider;
import io.rubrica.keystore.KeyStoreProvider;
import io.rubrica.sign.cms.DatosUsuario;
import io.rubrica.utils.FileUtils;
import io.rubrica.utils.X509CertificateUtils;
import java.io.File;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class POCSignServiceImpl implements POCSignService {

    private final String passwordFinal = "K0rea4k1978";
    X509CertificateUtils x509CertificateUtils = null;
    private String alias;
    private File documento;

    private int pagina;
    private String tipoFirma;
    private String razonFirma;

    @Override
    public void sign() {
        List<String> rutaDocumentos = existeDocumentos();
        if (rutaDocumentos.size() > 0) {
            firmarDocumento(rutaDocumentos);
        }
    }

    private List<String> existeDocumentos() {
        List<String> documentos = new ArrayList<>();

        String rutaDocumento = "/home/aperalta/Documents/pp/medical-services/files/doc3.pdf";
        if (rutaDocumento != null) {
            documento = new File(rutaDocumento);
            documentos.add(rutaDocumento);
        }

        return documentos;
    }

    private void firmarDocumento(List<String> rutaDocumentos) {
        List<String> documentosFirmados = new ArrayList<>();
        try {
            // Vemos si es un documento permitido primero
            // validacionPreFirmar();
            KeyStore ks = getKeyStore();

            if (ks != null) {
                alias = null;
                alias = CertUtils.seleccionarAlias(ks);
                X509Certificate x509Certificate = CertUtils.getCert(ks, alias);

                x509CertificateUtils = null;
                x509CertificateUtils = new X509CertificateUtils();
                if (x509CertificateUtils.validarX509Certificate(x509Certificate)) {
                    if (x509Certificate != null && alias != null) {
                        if (FileUtils.getFileExtension(documento).toLowerCase().equals("pdf")) {
                            tipoFirma = "Firma Visible";
                            //    previewPdf();
                            //  validacionPdf();

                        }
                        razonFirma = razonFirma == null ? "" : razonFirma;
                        char[] password = passwordFinal.toCharArray();

                        new Thread(
                            () -> {
                                //enableControles(false);
                                //creating ProgressMonitor instance
                                // ProgressMonitor pm = new ProgressMonitor(this, "Firmando", "Task starting", 1, rutaDocumentos.size());
                                int i = 0;
                                for (String rutaDocumento : rutaDocumentos) {
                                    try {
                                        i++;
                                        File documento = new File(rutaDocumento);
                                        String extDocumento = FileUtils.getFileExtension(documento);
                                        //updating ProgressMonitor note
                                        /*   pm.setNote("<html><b>" + i + " de " + rutaDocumentos.size() + " documento(s)</b>"
                                            + "<br>" + documento.toString() + "</html>");*/
                                        //updating ProgressMonitor progress
                                        //   pm.setProgress(i);
                                        /*      if (pm.isCanceled()) {
                                        //finalizar loop
                                        break;
                                    }*/
                                        byte[] docSigned = null;
                                        docSigned = FirmaDigital.firmar(ks, alias, documento, password, pagina, razonFirma, tipoFirma);
                                        if (docSigned != null) {
                                            if (extDocumento == "pdf") {
                                                String nombreDocFirmado = FileUtils.crearNombreFirmado(
                                                    documento,
                                                    FileUtils.getExtension(FileUtils.fileConvertToByteArray(documento))
                                                );
                                                FileUtils.saveByteArrayToDisc(docSigned, nombreDocFirmado);
                                                documentosFirmados.add(nombreDocFirmado);
                                            } else {
                                                String nombreDocFirmado = FileUtils.crearNombreFirmado(documento, "." + extDocumento);
                                                FileUtils.saveByteArrayToDisc(docSigned, nombreDocFirmado);
                                                documentosFirmados.add(nombreDocFirmado);
                                            }
                                            // Información del documento firmado y firmante

                                        }
                                    } catch (Exception e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                            }
                        )
                            .start();
                        //información firmante
                        DatosUsuario datosUsuario = CertEcUtils.getDatosUsuarios(x509Certificate);
                        System.out.println("datosUsuario: " + datosUsuario.toString());
                        //información firmante
                    }
                }
            }
        } catch (KeyStoreException e) {
            System.out.println(e.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private KeyStore getKeyStore() {
        KeyStore ks = null;
        try {
            String pathCertificate = "/home/aperalta/Documents/pp/medical-services/certificates/william_javier_zambrano_valencia.p12";
            File llave = new File(pathCertificate);
            if (llave.exists() == true) {
                KeyStoreProvider ksp = new FileKeyStoreProvider(pathCertificate);
                ks = ksp.getKeystore(passwordFinal.toCharArray());
            }
        } catch (KeyStoreException e) {
            System.out.println(e.getMessage());
        }
        return ks;
    }
}
