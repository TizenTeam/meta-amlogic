SUMMARY = "SysV init scripts for Amlogic framebuffer set-up"
DESCRIPTION = "Provides basic set-up for the amlogic framebuffer"
SECTION = "base"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=751419260aa954499f7abaabaa882bbe"

PR = "r1"

INHIBIT_DEFAULT_DEPS = "1"
RDEPENDS_${PN} = "initscripts"


SRC_URI = " \
                file://amlsetfb.sh \
                file://aaa.sh \
                file://GPLv2.patch \
"

COMPATIBLE_MACHINE = "(odroidc1|wetekplay)"

RDEPENDS_${PN}_append = " ${@bb.utils.contains('MACHINE_FEATURES', 'fb', 'fbset fbset-modes', '', d)}"

do_install() {
   if ${@bb.utils.contains('MACHINE_FEATURES','fb','true','false',d)}; then
       install -d ${D}${sysconfdir}/init.d
       install -m 0755 ${WORKDIR}/amlsetfb.sh  ${D}${sysconfdir}/init.d/amlsetfb.sh
       install -m 0755 ${WORKDIR}/aaa.sh  ${D}${sysconfdir}/init.d/aaa.sh
	   update-rc.d -r ${D} amlsetfb.sh start 03 S .
	   update-rc.d -r ${D} aaa.sh start 0 S .
   fi
}

