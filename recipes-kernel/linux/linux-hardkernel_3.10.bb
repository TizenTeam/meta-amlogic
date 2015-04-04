DESCRIPTION = "Linux kernel for the Hardkernel ODROID-C1 device"
SECTION = "kernel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

DEPENDS = "lzop-native"

# Mark archs/machines that this kernel supports
COMPATIBLE_MACHINE = "(odroidc1)"

inherit kernel siteinfo

# For device tree
require recipes-kernel/linux/linux-dtb.inc

# Avoid issues with Amlogic kernel binary components
INSANE_SKIP_${PN} += "already-stripped"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_STRIP = "1"

# from where to fetch the kernel
KERNEL_REPO_OWNER ??= "hardkernel"
KERNEL_REPO_URI ??= "git://github.com/${KERNEL_REPO_OWNER}/linux.git"
KBRANCH ?= "odroidc-3.10.y"

SRC_URI = " \
  ${KERNEL_REPO_URI};branch=${KBRANCH} \
  file://defconfig \
  file://0001-Revert-amlogic-s-dtb-make-modification.patch \
"
S = "${WORKDIR}/git/"


SRCREV = "c193f5d80656ce6d471cf3a28fe8259b3e3a02c0"

KV = "3.10.70"
PV = "${KV}+gitr${SRCPV}"
LOCALVERSION ?= ""

PR="r1"

do_install_append () {
        # remove *.z from installation path those are object files from amlogic for binary modules
        find ${D}/usr/src/kernel -type f -name "*.z" | xargs rm -f
}


PACKAGES =+ "kernel-headers"
FILES_kernel-headers = "${exec_prefix}/src/linux*"
