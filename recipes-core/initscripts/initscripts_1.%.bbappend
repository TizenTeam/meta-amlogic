FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append = " file://amlsetfb.sh \
"

RDEPENDS_${PN}_append = " ${@bb.utils.contains('MACHINE_FEATURES', 'fb', 'fbset fbset-modes', '', d)}"

do_install_append() {
   if ${@bb.utils.contains('MACHINE_FEATURES','fb','true','false',d)}; then
       install -m 0755 ${WORKDIR}/amlsetfb.sh  ${D}${sysconfdir}/init.d/amlsetfb
	   update-rc.d -r ${D} amlsetfb start 03 S .
   fi
}

