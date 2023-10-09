<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

    <xsl:output method="html"/>

    <xsl:template match="/">

        <html>
            <body>
                <table>

                    <tr>
                        <th>Boss Name</th>
                        <th>Location</th>
                        <th>HP</th>
                        <th>Poise</th>
                        <th>Souls</th>
                        <th>Drops</th>
                    </tr>
                    <xsl:for-each select="student/s">
                        <tr>
                            <th><xsl:value-of select="bossName"</th>
                            <th></th>
                            <th></th>
                            <th></th>
                            <th></th>
                            <th></th>
                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>

    </xsl:template>

</xsl:stylesheet>

