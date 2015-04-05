SSUMMARY = "Amlogic Codec libraries"
PACKAGE_ARCH = "${MACHINE_ARCH}"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

PR = "r1"

COMPATIBLE_MACHINE = "(wetekplay|odroidc1)"
DEPENDS = "alsa-lib"

inherit lib_package

# for DTS encoder: don't check for stripped , text relocations and ldflags
INSANE_SKIP_${PN} = "already-stripped textrel ldflags"

SRC_URI = "git://github.com/linux-meson/libamcodec.git \
           file://libamcodec.pc \
"
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = "'PREFIX=${STAGING_DIR_TARGET}'"

### NOTE:
do_install() {
    install -d ${D}${libdir}/pkgconfig
    install -m 0644 ${WORKDIR}/libamcodec.pc ${D}${libdir}/pkgconfig/
    install -d ${D}${includedir}/amlogic/amcodec
    install -d ${D}${libdir}
    #install -m 0755  ${S}/libamplayer.so  ${D}${libdir}
    install -m 0755 ${S}/amadec/acodec_lib/libdtsenc.so ${D}${libdir}
    install -m 0755 ${S}/amadec/libamadec.so ${D}${libdir}
    install -m 0755 ${S}/amavutils/libamavutils.so ${D}${libdir}
    install -m 0755 ${S}/amcodec/libamcodec.so.0.0 ${D}${libdir}
    cp -pR  ${S}/amcodec/include/* ${D}${includedir}/amlogic/amcodec

    if ${@bb.utils.contains('MACHINE_FEATURES','meson-m8','true','false',d)}; then
        install -d ${D}${base_libdir}/firmware
        install -m 0644 ${S}/amadec/firmware-m8/*.bin  ${D}${base_libdir}/firmware/
    fi
    if ${@bb.utils.contains('MACHINE_FEATURES','meson-m6','true','false',d)}; then
        install -d ${D}${base_libdir}/firmware
        install -m 0644 ${S}/amadec/firmware-m6/*.bin  ${D}${base_libdir}/firmware/
    fi

}

FILES_${PN} = "${libdir}/* \
               ${base_libdir}/firmware/ \
"
FILES_${PN}-dev = "${includedir}/*"

