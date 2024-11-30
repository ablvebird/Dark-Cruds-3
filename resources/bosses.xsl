<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/bosses">
        <html>
            <head>
                <title>Bosses</title>
            </head>
            <body>
                <h1>Dark Souls 3 Entities.Boss List</h1>
                <table border="1">
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Location</th>
                        <th>HP</th>
                        <th>Poise</th>
                        <th>Souls</th>
                        <th>Drop Name</th>
                        <th>Description</th>
                    </tr>
                    <xsl:apply-templates select="boss" />
                </table>
            </body>
        </html>
    </xsl:template>

    <xsl:template match="boss">
        <tr>
            <td><xsl:value-of select="@bossId" /></td>
            <td><xsl:value-of select="bossName" /></td>
            <td><xsl:value-of select="location" /></td>
            <td><xsl:value-of select="stats/HP" /></td>
            <td><xsl:value-of select="stats/Poise" /></td>
            <td><xsl:value-of select="stats/Souls" /></td>
            <td><xsl:value-of select="drop/dropName" /></td>
            <td><xsl:value-of select="drop/description" /></td>
        </tr>
    </xsl:template>

</xsl:stylesheet>
