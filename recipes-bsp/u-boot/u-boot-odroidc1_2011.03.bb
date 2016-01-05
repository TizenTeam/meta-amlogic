require recipes-bsp/u-boot/u-boot.inc

LIC_FILES_CHKSUM = "file://COPYING;md5=1707d6db1d42237583f50183a5651ecb"

UBOOT_REPO_URI ?= "git://github.com/hardkernel/u-boot.git"
UBOOT_BRANCH ?= "odroidc-v2011.03"

COMPATIBLE_MACHINE = "(odroidc1)"

SRCREV = "f631c80969b33b796d2d4c077428b4765393ed2b"

PV = "v2011.03+git${SRCPV}"
PR = "r7"

PROVIDES =+ "u-boot"
PACKAGES =+ "u-boot-ini"

SRC_URI = " \
    ${UBOOT_REPO_URI};branch=${UBOOT_BRANCH} \
    file://0001-ucl-use-host-compiler-supplied-by-OE.patch \
    file://0003-use-lldiv-for-64-bit-division.patch \
    file://0004-Loading-bootlogo-with-ext4load-instead-of-movi.patch \
    file://boot.ini \
    file://bootlogo.bmp \
"

# check for hardfp
SRC_URI_append = " ${@bb.utils.contains('TUNE_FEATURES','callconvention-hard',' file://0002-added-hardfp-support.patch ','',d)}"

EXTRA_OEMAKE += 'HOSTCC="${BUILD_CC}"'

PARALLEL_MAKE = ""

do_compile_append () {
    # Move result to usual location
    mv ${B}/sd_fuse/${UBOOT_BINARY} ${B}
}

BL1_SUFFIX ?= "bin.hardkernel"
BL1_IMAGE ?= "bl1-${MACHINE}-${PV}-${PR}.${BL1_SUFFIX}"
BL1_BINARY ?= "bl1.${BL1_SUFFIX}"
BL1_SYMLINK ?= "bl1-${MACHINE}.${BL1_SUFFIX}"

do_install_append () {
    install -d ${D}/boot/
    install ${WORKDIR}/boot.ini ${D}/boot/boot.ini
    install ${WORKDIR}/bootlogo.bmp ${D}/boot/
}

do_deploy_append () {
    install ${WORKDIR}/boot.ini ${DEPLOYDIR}
    install ${S}/sd_fuse/${BL1_BINARY} ${DEPLOYDIR}/${BL1_IMAGE}
    cd ${DEPLOYDIR}
    rm -f ${BL1_BINARY} ${BL1_SYMLINK}
    ln -sf ${BL1_IMAGE} ${BL1_SYMLINK}
    ln -sf ${BL1_IMAGE} ${BL1_BINARY}
}

FILES_u-boot-ini = "/boot/boot.ini \
                   /boot/bootlogo.bmp \
"

PACKAGE_ARCH = "${MACHINE_ARCH}"

