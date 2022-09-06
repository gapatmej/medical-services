package com.customsoftware.medicalservices.service.impl;

import com.customsoftware.medicalservices.domain.User;
import com.customsoftware.medicalservices.service.ServiceUtils;
import com.customsoftware.medicalservices.service.SignService;
import com.customsoftware.medicalservices.web.rest.errors.MedicalServicesRuntimeException;
import ec.gob.firmadigital.cliente.FirmaDigital;
import io.rubrica.certificate.CertUtils;
import io.rubrica.keystore.FileKeyStoreProvider;
import io.rubrica.keystore.KeyStoreProvider;
import io.rubrica.utils.FileUtils;
import io.rubrica.utils.X509CertificateUtils;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SignServiceImpl extends AbstractServiceImpl implements SignService {

    private static final Logger LOGGER = Logger.getLogger(SignService.class.getName());
    private final String passwordFinal = "K0rea4k1978";
    private final int page = 0;
    X509CertificateUtils x509CertificateUtils = null;
    private String alias;
    private String signType = "Firma Visible";
    private String razonFirma;

    public SignServiceImpl() {
        super(SignServiceImpl.class);
    }

    @Override
    public void sign(String path, User user) {
        List<String> paths = validatePath(path);
        signDocuments(paths, user);
    }

    private List<String> validatePath(String pathString) {
        Path path = Paths.get(pathString);
        if (!Files.exists(path)) {
            throw new MedicalServicesRuntimeException("File to sign not exists");
        }

        List<String> documents = new ArrayList<>();
        documents.add(pathString);
        return documents;
    }

    private void signDocuments(List<String> paths, User user) {
        List<String> documentosFirmados = new ArrayList<>();
        try {
            // Vemos si es un documento permitido primero
            // validacionPreFirmar();
            KeyStore ks = getKeyStore(user);

            if (ks != null) {
                alias = null;
                alias = CertUtils.seleccionarAlias(ks);
                X509Certificate x509Certificate = CertUtils.getCert(ks, alias);

                x509CertificateUtils = null;
                x509CertificateUtils = new X509CertificateUtils();
                if (x509CertificateUtils.validarX509Certificate(x509Certificate)) {
                    if (x509Certificate != null && alias != null) {
                        /*  if (FileUtils.getFileExtension(documento).toLowerCase().equals("pdf")) {
                            tipoFirma = "Firma Visible";
                            //    previewPdf();
                            //  validacionPdf();

                        }*/
                        razonFirma = razonFirma == null ? "" : razonFirma;
                        char[] password = passwordFinal.toCharArray();

                        new Thread(
                            () -> {
                                //enableControles(false);
                                //creating ProgressMonitor instance
                                // ProgressMonitor pm = new ProgressMonitor(this, "Firmando", "Task starting", 1, rutaDocumentos.size());
                                for (String rutaDocumento : paths) {
                                    try {
                                        File doc = new File(rutaDocumento);
                                        String extDocumento = FileUtils.getFileExtension(doc);
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
                                        docSigned = FirmaDigital.firmar(ks, alias, doc, password, page, razonFirma, signType);
                                        if (docSigned != null) {
                                            if (extDocumento == "pdf") {
                                                String nombreDocFirmado = FileUtils.crearNombreFirmado(
                                                    doc,
                                                    FileUtils.getExtension(FileUtils.fileConvertToByteArray(doc))
                                                );
                                                FileUtils.saveByteArrayToDisc(docSigned, nombreDocFirmado);
                                                documentosFirmados.add(nombreDocFirmado);
                                            } else {
                                                String nombreDocFirmado = FileUtils.crearNombreFirmado(doc, "." + extDocumento);
                                                FileUtils.saveByteArrayToDisc(docSigned, nombreDocFirmado);
                                                documentosFirmados.add(nombreDocFirmado);
                                            }
                                            // Información del documento firmado y firmante

                                        }
                                    } catch (Exception e) {
                                        log.error(e.getMessage());
                                    }
                                }
                            }
                        )
                            .start();
                    }
                }
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

    private KeyStore getKeyStore(User user) {
        KeyStore ks = null;
        try {
            String pathCertificate = ServiceUtils.getCertificatePath(user);
            File llave = new File(pathCertificate);
            if (llave.exists() == true) {
                KeyStoreProvider ksp = new FileKeyStoreProvider(pathCertificate);
                ks = ksp.getKeystore(passwordFinal.toCharArray());
            }
        } catch (KeyStoreException e) {
            log.error(e.getMessage());
        }
        return ks;
    }
}
