# Objectif du fichier
Détailler comment construire notre framework

Pour construire notre framework il suffit :

* D'ajouter les sources, le pom.xml et le banc de test de votre projet dans le module **sample**
* D'ajouter une référence vers notre plugin Maven dans le pom.xml de votre projet comme ceci :

>        <plugin>
            <groupId>fr.inria.gforge.spoon</groupId>
            <artifactId>spoon-maven-plugin</artifactId>
            <version>2.2</version>
            <executions>
                <execution>
                  <phase>generate-sources</phase>
                <goals>
                  <goal>generate</goal>
                </goals>
              </execution>
            </executions>
            <configuration>
              <processors>
                <processor>${param_processor}
                </processor>
              </processors>
            </configuration>
            <dependencies>
            <dependency>
              <groupId>com.mnt2.mutationFramework</groupId>
              <artifactId>mnt2_MutationFramework</artifactId>
              <version>1.0-SNAPSHOT</version>
            </dependency>
              <dependency>
                <groupId>fr.inria.gforge.spoon</groupId>
                <artifactId>spoon-core</artifactId>
                <version>5.0.2</version>
              </dependency>
            </dependencies>
        </plugin>
        

