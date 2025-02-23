import { Component } from '@angular/core';
import { FileUploadService } from '../../services/file-upload.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-file-upload',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './file-upload.component.html',
  styleUrls: ['./file-upload.component.scss']
})
export class FileUploadComponent {
  selectedFile: File | null = null;
  validFormat: boolean = false;
  uploadSuccess: boolean = false;
  errorMessage: string = '';
  fileName: string = '';
  responses: any[] = [];

  constructor(private fileUploadService: FileUploadService) {}

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
    if (this.selectedFile) {
      this.fileName = this.selectedFile.name;
      this.validateFile(this.selectedFile);
    }
  }

  validateFile(file: File) {
    const reader = new FileReader();
    reader.onload = (e) => {
      const content = (e.target?.result as string).trim();
      const lines = content.split('\n');
      this.validFormat = lines.every(line => {
        const parts = line.split(',');
        return parts.length === 6;
      });

      if (!this.validFormat) {
        this.errorMessage = 'Formato incorrecto o fecha inválida. La fecha debe ser menor o igual a la actual.';
      } else {
        this.errorMessage = '';
      }
    };
    reader.readAsText(file);
  }

  uploadFile(event?: Event) {
    event?.preventDefault();
    if (this.selectedFile && this.validFormat) {
      const reader = new FileReader();
      reader.onload = (e) => {
        const content = (e.target?.result as string).trim();
        const lines = content.split('\n');

        const clientes = lines.map(line => {
          const [nombre, apellido, identificacion, edad, fechaRegistro, correo] = line.split(',').map(item => item.trim());
          return {
            nombre,
            apellido,
            identificacion,
            edad: Number(edad),
            fechaRegistro,
            correo
          };
        });

        this.fileUploadService.uploadClients(clientes).subscribe((response: any) => {
          console.log('Archivo procesado:', response);
          this.responses = response;
          this.uploadSuccess = true;
          this.errorMessage = '';
        }, (error: any) => {
          console.error('Error al procesar el archivo:', error);
          this.errorMessage = 'Error al procesar el archivo';
          this.uploadSuccess = false;
        });
      };
      reader.readAsText(this.selectedFile);
    } else {
      this.errorMessage = 'Seleccione un archivo válido';
      this.uploadSuccess = false;
    }
  }
}
