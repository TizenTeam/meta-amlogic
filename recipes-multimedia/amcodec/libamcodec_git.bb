SSUMMARY = "Amlogic Codec libraries"
PACKAGE_ARCH = "${MACHINE_ARCH}"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

PR = "r3"

COMPATIBLE_MACHINE = "(wetekplay|odroidc1)"
DEPENDS = "alsa-lib"

inherit autotools-brokensep

# for DTS encoder: don't check for stripped , text relocations and ldflags
INSANE_SKIP_${PN} = "already-stripped textrel ldflags"

SRC_URI = "git://github.com/linux-meson/libamcodec.git \
           file://libamcodec.pc \
"
SRCREV = "${AUTOREV}"
PV = "v2014.05.27+git${SRCPV}"
S = "${WORKDIR}/git"

EXTRA_OEMAKE = "'PREFIX=${STAGING_DIR_TARGET}'"

### NOTE:
do_install() {
    install -d ${D}${libdir}/pkgconfig
    install -m 0644 ${WORKDIR}/libamcodec.pc ${D}${libdir}/pkgconfig/
    install -d ${D}${includedir}/amlogic/amcodec
    install -d ${D}${includedir}/amlogic/ppmgr
    install -d ${D}${includedir}/amlogic/amports
    install -d ${D}${libdir}
    #install -m 0755  ${S}/libamplayer.so  ${D}${libdir}
    install -m 0755 ${S}/amadec/acodec_lib/libdtsenc.so ${D}${libdir}
    install -m 0755 ${S}/amadec/libamadec.so ${D}${libdir}
    install -m 0755 ${S}/amavutils/libamavutils.so ${D}${libdir}
    install -m 0755 ${S}/amcodec/libamcodec.so ${D}${libdir}
    install -m 0755 ${S}/amcodec/include/*.h ${D}${includedir}/amlogic/amcodec
    install -m 0755 ${S}/amcodec/include/ppmgr/*.h ${D}${includedir}/amlogic/ppmgr
    install -m 0755 ${S}/amcodec/include/amports/*.h ${D}${includedir}/amlogic/amports
    # Fix include path
    sed -i 's/^#include "/#include "amcodec\//' ${D}${includedir}/amlogic/amcodec/codec.h

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

